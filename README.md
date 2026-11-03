# 农作物品种信息管理系统（crop-management）

基于 **SpringBoot 3 + MyBatis-Plus** 的农作物品种信息管理后端系统，实现品种的增删改查、分页查询、动态多条件查询、参数校验、全局异常统一处理与日志规范输出。

## 一、项目介绍

本项目为《基于 Trae 的 SpringBoot 综合开发实训作业》（农业方向·选项 A：农作物品种信息管理）。

核心实体 `Crop`（农作物）字段：

| 字段 | 说明 |
| --- | --- |
| id | 主键，数据库自增 |
| cropName | 作物名称，如：水稻、玉米 |
| varietyName | 品种名称，如：隆平 206 |
| cropType | 作物类型：粮食 / 蔬菜 / 水果 / 油料 |
| suitArea | 适宜种植区域 |
| growthCycle | 生长周期（天） |
| yieldLevel | 亩产预估（kg） |
| plantStatus | 品种推广状态：0 停推 1 推广 |
| createTime / updateTime | 创建 / 更新时间，自动填充 |
| deleted | 逻辑删除标记（0 未删除 1 已删除） |

## 二、技术栈版本

| 技术 | 版本 |
| --- | --- |
| JDK | 21 |
| SpringBoot | 3.4.5（当前稳定版） |
| MyBatis-Plus | 3.5.9（spring-boot3-starter） |
| MySQL | 5.7+ / 8.0 |
| 参数校验 | jakarta.validation（spring-boot-starter-validation） |
| 日志 | SLF4J + Logback（SpringBoot 默认） |
| 构建工具 | Maven（内含 Maven Wrapper，无需本机安装） |

## 三、环境说明

1. JDK 21
2. MySQL 5.7 及以上
3. Maven 3.8+（或直接使用项目自带 mvnw）

## 四、启动步骤

```bash
# 1. 初始化数据库：在 MySQL 中执行 sql/init.sql（自动建库 agri_db、建表、插入测试数据）
mysql -uroot -p < sql/init.sql

# 2. 修改 src/main/resources/application.yml 中数据库账号密码（默认 root / 123456）

# 3. 启动（Windows 用 mvnw.cmd，Linux/Mac 用 ./mvnw）
mvnw.cmd spring-boot:run

# 4. 看到日志 “Started AgriApplication” 即启动成功，默认端口 8080
```

## 五、接口说明

统一返回结构：

```json
{ "code": 200, "msg": "操作成功", "data": {} }
```

| 接口 | 请求方式 | 路径 | 说明 |
| --- | --- | --- | --- |
| 新增农作物品种 | POST | /crop/add | 接收 CropDTO，@Valid 参数校验 |
| 修改农作物品种 | PUT | /crop/update?id=1 | 接收 CropDTO |
| 根据 id 查询 | GET | /crop/{id} | 返回 CropVO |
| 删除品种 | DELETE | /crop/{id} | 逻辑删除 |
| 分页动态查询 | GET | /crop/list | pageNum、pageSize、cropName、varietyName、cropType、plantStatus 均可省略 |

### 接口示例

新增（POST http://localhost:8080/crop/add）：

```json
{
  "cropName": "水稻",
  "varietyName": "隆平206",
  "cropType": "粮食",
  "suitArea": "湖南、湖北",
  "growthCycle": 130,
  "yieldLevel": 650,
  "plantStatus": 1
}
```

校验失败返回示例：

```json
{ "code": 400, "msg": "生长周期不能小于1天", "data": null }
```

分页动态查询示例（条件全部可省略，传了才拼进 SQL）：

```
GET /crop/list?pageNum=1&pageSize=5&cropName=水稻&plantStatus=1
```

## 六、Entity / DTO / VO 分层说明

| 层 | 类 | 职责 |
| --- | --- | --- |
| Entity | `entity/Crop.java` | 与数据库表 `crop` 一一对应；MyBatis-Plus 注解（`@TableName`/`@TableId`/`@TableField`/`@TableLogic`）；`createTime`/`updateTime` 由 MetaObjectHandler 自动填充；**不做校验、不直接返回前端** |
| DTO | `dto/CropDTO.java` | 接收前端新增/修改表单；承载全部校验规则（`@NotBlank`、`@Size`、`@Min`、`@Max`、`@Pattern`），校验失败由全局异常处理器返回友好提示 |
| VO | `vo/CropVO.java` | 返回给前端的视图对象；与 Entity 解耦，**屏蔽 deleted 等数据库内部字段**，并组合出 `plantStatusDesc`（推广中/已停推）展示字段 |

## 七、关键功能说明

### 1. 动态多条件查询

使用 MyBatis-Plus `LambdaQueryWrapper`，**传参不为空则拼接条件，为空不拼接**，禁止写死 SQL：

```java
LambdaQueryWrapper<Crop> wrapper = Wrappers.lambdaQuery();
if (StringUtils.hasText(cropName))    { wrapper.like(Crop::getCropName, cropName); }
if (StringUtils.hasText(varietyName)) { wrapper.like(Crop::getVarietyName, varietyName); }
if (StringUtils.hasText(cropType))    { wrapper.eq(Crop::getCropType, cropType); }
if (plantStatus != null)              { wrapper.eq(Crop::getPlantStatus, plantStatus); }
```

### 2. 全局异常处理

`@RestControllerAdvice` 统一捕获三类异常并返回统一 JSON：

- `MethodArgumentNotValidException`：DTO 校验失败 → 400 + 友好提示
- `BusinessException`：业务异常（如品种不存在）→ 400 + 自定义提示
- `Exception`：未知异常兜底 → 500 + error 日志输出完整堆栈

### 3. 分页

- `MybatisPlusConfig` 注册 `PaginationInnerInterceptor`（分页插件，物理分页）
- 返回统一封装 `PageResult`：总条数 total、总页数 pages、当前页 pageNum、数据列表 list

### 4. 参数校验（JSR380）

DTO 层使用 `jakarta.validation` 注解：非空（`@NotBlank`/`@NotNull`）、长度（`@Size`）、数值范围（`@Min`/`@Max`，生长周期 1~365、亩产非负）、格式（`@Pattern` 枚举作物类型），Controller 入参 `@Valid` 触发。

### 5. 日志规范

- SLF4J + Logback，Controller 打印入参 info 日志，Service 打印关键业务节点日志
- 异常打印 error 日志并输出堆栈
- 全项目禁止 `System.out.println`（仅启动类用 `System.Logger` 输出启动横幅，属 JDK 标准日志门面）

### 6. 其他

- 逻辑删除：`@TableLogic`，DELETE 接口执行 UPDATE deleted=1
- 自动填充：`MyMetaObjectHandler` 自动维护 createTime/updateTime

## 八、工程结构

```
com.agri
├── AgriApplication.java          // 启动类
├── config
│   ├── MybatisPlusConfig.java    // MP 分页插件配置
│   └── MyMetaObjectHandler.java  // MP 自动填充处理器
├── entity
│   └── Crop.java                 // 数据库实体
├── dto
│   └── CropDTO.java              // 前端提交数据（新增、修改），带校验
├── vo
│   └── CropVO.java               // 返回前端视图对象
├── mapper
│   └── CropMapper.java           // 继承 BaseMapper
├── service
│   ├── CropService.java
│   └── impl/CropServiceImpl.java
├── controller
│   └── CropController.java
├── exception
│   ├── BusinessException.java    // 自定义业务异常
│   └── GlobalExceptionHandler.java // 全局异常处理器
├── common
│   ├── Result.java               // 统一返回结果封装
│   └── PageResult.java           // 分页返回封装
sql/init.sql                       // 建库建表 + 测试数据
```
