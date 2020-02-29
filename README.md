#### 工具类 
* 生成pdf文件 
> SpringbootApplicationTests

* 状态机
> MassOutHeadStatusTest

* http调用(加签，验签，xml报文拼装)
> EpccSingleServiceTest

* 动作链处理器(多个处理器按顺序执行)
> ProcessorController

* execl上传下载工具
> execl包 
> ExeclDownLoadController

* 加密工具类
> RSA加密 RSAUtils
> DES加密 DesEncryptUtil

#### lambda表达式
* 简单的遍历及过滤操作 
> PersonListTest 

### 统一参数校验
* 统一接口参数校验
> MomalValidateController.nomal

* 自定义参数校验
> MomalValidateController.special

### junit日志接入
* jar包引入
> mockito-all和jacoco-maven-plugin引入，plugin添加，详见pom文件
> 具体InjectMocks和Mock测试添加，详细见test类文件
> 编译后具体的测试覆盖文件见target-site-jacoco-index.html

### 
> [如何设计好接口](/resources/doc/interface.md)