- # Smart Campus 后端规则（smart-campus-java）

  Spring Boot 3 + MyBatis（自定义泛型 Mapper，**不是 MyBatis Plus**）+ MySQL 8 + Redis。

  # 模块与包路径

  | 模块                | 包路径               | 职责                                                         |
  | ------------------- | -------------------- | ------------------------------------------------------------ |
  | smart-campus-common | `com.smart.campus.*` | PO / DTO / VO / Query、Service / Mapper、Redis 组件、异常、枚举、工具类 |
  | smart-campus-admin  | `com.smart.admin.*`  | 管理端 Controller、Biz、管理端专用 DTO/VO；独立启动          |
  | smart-campus-web    | `com.smart.web.*`    | 用户端 Controller、Biz、用户端专用 DTO/VO；独立启动          |

  依赖方向：`admin → common`、`web → common`。common 不允许反向依赖端模块。

  启动类位于各端模块根包；扫描范围覆盖 `com.smart`，Mapper 扫描指向 `com.smart.campus.mappers`。

  # 分层规范

