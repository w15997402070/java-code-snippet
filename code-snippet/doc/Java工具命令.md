# java工具命令

## JPS

`jps [options] [hostid]`

`jps -l`
* `-l` : 只输出LVMID,省略主类
* `-m` : 输出虚拟机进程启动时传递给主类main()函数的参数
* `-l` : 输出主类的全名,如果进程执行的是jar包,则输出jar包路径
* `-v` : 输出虚拟机进程启动时JVM的参数

## jstat

虚拟机统计信息监视工具

jstat : JVM Statistics Monitoring Tool

`jstat [options vmid [interval[s|ms] [count]]]`
* interval : 查询间隔
* count : 次数

`jstat -gc 2764 250 20`
表示每250毫秒查询一次进程2764垃圾收集情况,一共查询20次

远程虚拟机进程VMID的格式:
`[protocol]:[//]lvmid[@hostname[:port]/servername]`

jstat工具命令参数选项 :
//todo

## jinfo
Java配置信息工具

## jmap
java 内存映像工具

## jhat

虚拟机堆转储快照分析工具

## jstack

java堆栈跟踪工具

Thread.getAllStackTraces()方法获取虚拟机中所有线程的StackTraceElement对象





