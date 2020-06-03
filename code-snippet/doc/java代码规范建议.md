# java代码规范建议

## 命名

### 变量名
变量名应该是名词,能够正确地描述业务,有表达力,如果一个变量名需要注释来补充说明,那么很有可能命名有问题

### 函数名

函数命名要具体,

封装判断

```java
if(customer.getUserId().equals(NIL_VALUE)
    && customer.getCustomerGroup() != CustomerGroup.CANCEL_GROUP){
    //doSomething
}
//可以将if里面的判断封装成一个函数
```

函数参数尽量少,如果三个以上就可以封装成类

函数要短小,对于java,建议一个方法在20行以内

职责单一,一个方法只做一件事



### 类名

实体类
辅助类

### 保持一致性

保持命名的一致性

每个概念对应一个词,并且一以贯之
例如 : fetch, retrieve, get, find, query都可以表示查询的意思,如果不加约束地给多个类中的同种查询方法命名,就会不记得是哪个类的方法
建议方法命名约定 :
CRUD操作
新增 : create
添加 ; add
删除 : remove
修改 : update
查询(单个结果) : get
查询(多个结果) : list
分页查询 : page
统计 : count

使用对仗词;
* add/remove
* increment/decrement
* open/close
* begin/end
* insert/delete
* create/destroy
* lock/unlock
* source/target
* first/last
* min/max
* start/stop
* get/set
* next/previous
* up/down
* old/new

### 后置限定词

Total, Sum, Average, Max, Min这样的限定词加到名字的最后

### 统一技术语言

包括DO, DAO, DTO, Service, ServiceImpl, Component, Repository等先约定好


## 自明的代码
好的代码是最好的文档

## 注释

如果注释是阐述代码背后的用意,那么这个注释是有用的
如果注释是复述代码的功能,那么就要小心,这样的注释往往意味着坏味道

1. 不要复述功能
为了复述功能而存在的注释,主要作用的弥补我们表达意图时遭遇的失败,
如果编程语言有表达力,或者我们擅长用代码显示的表达意图,那么也许根本不需要注释
2. 解释背后意图
注释要能够解释代码背后的意图,而不是对功能的简单重复

## 命名工具



## 抽象

层次越高,抽象层次越高,它所包含的东西就越多,含义越宽泛,忽略的细节也越多

层次越往下,抽象程度越低,它所包含的东西越少,细节越多

内涵越小,外延越大,内涵越大,外延越小

### 如何抽象

1. 寻找共性,合并同类项,归并分类,寻找共性
2. 提升抽象层次,当发现有些东西无法归到一个类时,可以通过上升一个抽象层次,让他们在更高的抽象层次上产生逻辑关系



有目标

看一个项目代码时有目标去做什么,要了解什么,要获得什么



https://github.com/alibaba/COLA


