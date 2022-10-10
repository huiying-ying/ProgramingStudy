# 传输控制协议/网际协议 （TCPIP）

- 传输控制协议（Transmission Control  Protocol, TCP） -- 负责高层功能，如**分段**、**重装**、**差错检测**
- 网际协议（Internet Protocol, IP）-- 处理数据报的路由选择

###### ISP  -- 因特网服务提供者

###### NAP  -- 网络接入点（Network Access Point）

###### ISO  -- 国际标准化组织

###### NIC   -- 网络接口卡（以太网中的48位物理地址）

**协议**：用以管理通信的一组规则。三个关键要素为**语法**、**语义**、**时序**。

**标准**：保证数据与电信技术与处理过程在国内和国际上的互操作性。

#### OSI模型——开放系统互联（Open Systems Interconnection）

| 物理层     | 协调通过物理媒体传送比特流时所需要的各种功能 |                                                        |
| ---------- | -------------------------------------------- | ------------------------------------------------------ |
| 数据链路层 | 把物理层转换为可靠的链路                     | 组帧、物理编址、流量控制、差错控制、接入控制           |
| 网络层     | 负责把分组从源点交付到终点                   | 逻辑编址、路由选择                                     |
| 运输层     | 负责完整报文的进程到进程的交付               | 服务点编址、分段与重装、连接控制、流量控制、差错控制   |
| 会话层     | 建立、维持并同步正在通信的系统之间的交互     | 对话控制、同步                                         |
| 表示层     | 两个系统所交换信息的语法和语义               | 转换、加密、压缩                                       |
| 应用层     | 让用户接入网络                               | 网络虚拟终端、文件传送、存取和管理、邮件服务、名录服务 |

#### 网络层的分组交换

1. 数据报方式——**无连接服务**的网络中的交换机是**路由器**。无连接分组交换网中，转发判决的依据是该分组的**目的地址**。
2. 虚电路方式——面向连接的服务三个阶段：建链、数据传送、拆链。面向连接的分组交换网中，转发判决的依据是该**分组的标号**。

##### 源计算机 网络层提供的服务

**分组化处理**：基于上层给到的信息（包括目的逻辑地址），补充源逻辑地址、协议号、分片信息等，作为数据报首部，另外还有针对这个首部的检验和。把上层的数据和刚刚生成的首部封装到一个数据报中。

**查找下一跳的逻辑地址**：基于路由表找出下一跳的逻辑地址，该地址可能是本网络中的一个路由器（当目的逻辑地址不在本网络中时）。

**查找下一跳的物理地址**：将下一跳的逻辑地址映射为Mac地址，便于数据链路层实施真正意义上的交付（后续地址解析协议 ARP 可对该任务作出补充）。

**分片**：解决帧（数据链路层传输单元）的数据部分最大长度（MTU）限制了网络层的数据规模，这个时候需要对原始数据进行分片。

#### 网络层的服务

- 差错控制：数据报首部的检验和（勉强算是在检查）

- 流量控制：没有

- 拥塞控制：

  - 无连接网络——信令（通知发送方放慢速度，非网络层）、扼流分组（ICMP，网络层）

  - 面向连接网络——建立额外的虚电路，建立连接时协商通信量

  - 路由协议：建立、维护、更新路由表，使用网络层、传输层的服务，分单播、多播两种。

ICMP——差错控制（第9章）

子网——把A、B类网络划分为小的几个子网

超网——把C类网络结合在一起化为相对大的超网

###### NAT（network address translation，网络地址转换）

提供在专用地址和全球地址的互相映射，同时也支持虚拟专用网络。

NAT路由器负责专用网络和全球网络通信过程中的地址转换，转换过程主要依赖**转换表**（包括专用地址、专用端口、外部地址、外部端口、运输层协议等信息），但该表只能在专用网络想外部发出请求时可以建立表项，因此NAT技术**不能用于专用网络中的服务器为外部网络提供服务**。

#### IP分组的交付和转发

##### 交付（delivery）

——在网络层的控制下，底层对分组的处理方式。如直接交付和间接交付。

- 直接交付：分组的终点和交付者在同一网络中，是在终点的IP地址和终点的物理地址之间进行的地址映射
- 间接交付：目的主机和交付者不在同一网络上，是在下一个路由器的IP地址与下一个路由器的物理地址之间进行的地址映射

##### 转发（forwarding）

——把分组交付到下一站的方式。

- 基于目的地址

  转发技术：下一跳方法（只保留下一跳而不是整个路由）、特定网络方法（对在一个网络中的主机只建立一个表项）、特定主机方法（对特殊主机格外标注）、默认方法（剩余的部分）

  tire——线索，一种特殊的树（数据结构），能够高效地查找路由

- 基于标记

  MPLS（多协议标记交换，Multi-Protocol Label Switching）

  将 IP 数据报封装在MPLS中，即在原来网络层和数据链路层之间增加一个层，通过MPLS的头部增加**标记**字段。标记堆栈可实现多级交换。

#### IPV4——IP数据报格式

分片

MTU——以太网局域网为1500字节，FDDI局域网为4352字节，PPP为296字节。

与分组的概念不同，分片发生在传输过程中的数据链路层，并且是在不同类型的网络之间。传输期间数据报可能在分片的基础上再分片，但重装只会在终点完成。

#### ARP（地址解析协议）

将IP协议中的逻辑地址映射为物理地址。

ARP请求采用广播发送，ARP回答采用单播发送。

——用下一跳地址和接口号来找出下一个路由器的物理地址

##### 包含四种情况

1. **同网络**主机向未知物理地址的主机发送数据，此时ARP请求的应答是**目的主机的物理地址**
2. **不同网络**向未知物理地址的主机发送数据，要先经过本网络的路由器，此时ARP请求的应答是**本网络路由器的物理地址**
3. **路由器**作为不同网络之间数据传输的**途径点**，ARP请求的应答是**下一个路由器的物理地址**
4. 不同网络的传输过程中，路由器向目的主机交付，此时ARP请求的应答是**目的主机的物理地址**

###### 代理 ARP 

——可产生子网划分的效果，即在进行ARP应答时，声称**代理ARP路由器**自己的物理地址就是目的硬件地址。

#### ATM （异步传递方式，Asynchronous Transfer Mode）

##### 基本信息

交换广域网的一种，除此之外还有 X.25，帧中继。

**信元网络**（信元——**很小**且**长度固定**的数据单元），以信元为数据交换的基本单位，此时ARP请求的应答是**目的主机的物理地址**。

采用异步TDM技术（异步时分复用），端点之间的连接是通过**传输路径（TP）**，**虚通道（VP）**以及**虚电路（VC）**完成，即TP中有多条TP，TP中又有多条 VC ，一条虚连接可以由**虚通道标识符（VPI）**和**虚电路标识符（VCI）**指明。

##### ATM网络分为3层：应用适配层、ATM层以及物理层。

###### 应用适配层

允许现有网络（如分组交换网）与ATM连接，其中一种叫AAL5，可用于运载因特网中的IP分组，是无连接的分组协议。

###### ATM层

提供路由选择、通信量管理、交换和复用等服务。

###### 物理层

定义了传输媒体、比特传输、编码方式以及电信号和光信号的转换。

##### IP在ATM上运行

AAL5的效率比简单封装更高

路由选择需要基于IP地址（ATM网络中没有作用，仅为IP协议指明路由器）、物理地址（ATM网络中的，20字节）、虚电路标识符（VPI和VCI）

##### ATMARP

用于找出ATM广域网中**给定IP地址**的**离去点路由器**的**物理地址**，与ARP的区别是**ARP利用了局域网广播能力**来找出映射，而ATMARP则是采用PVC和SVC两种方式连接两个路由器。

PVC（永久虚电路）

交换机的路由表中有两个端点之间的 VPI 和 VCI ，因此无需ATMARP服务器，发送方发送反向请求报文，另一端接收后发送反向回答报文，即可映射到离去点物理地址，同时在路由器建立表项。

SVC（交换虚电路）

此时两者之间没有已有的虚电路，因此需要通过ATMARP服务器。发送方首先向服务器发送ATMARP请求报文，服务器找到后给出应答（离去点的物理地址），找不到就返回 ATMARP NACK 报文。

#### ICMP（网际控制报文协议）

##### 差错报告报文

用途：针对终点不可达、源点抑制、超时、参数问题、改变路由这5种类型的差错，将引起差错的原始分组的信息发送给数据报源点。

1. 终点不可达——包括多种原因，由代码表示不同原因，一部分由目的主机发起ICMP报文，一部分由路由器发起
2. 源点抑制——通知源点因拥塞问题丢弃了数据报，需要放慢发送速度
3. 超时——可能由于数据报的生存时间减为0（路由器使用），也可能由于有分片没有在时限内全部到达（目的主机使用）
4. 参数问题——差错或者多义、缺少参数的情况下（主机和路由器均可使用）
5. 改变路由——主机路由表的更新、增大过程依赖于改变路由，此时数据报其实并未丢弃（由路由器向同网络的主机发送，用于更新路由）

数据：原始数据报的IP首部 + 该数据报的前8个字节（包括端口号和序号的信息，提供给上层）

##### 查询报文

###### 回送请求与回答

用于检查两台机器在IP级能否彼此通信，检查另一个主机是否可达（ping）

###### 时间戳请求和回答

用于确定IP数据报在两个机器见来回需要往返的时间，或者同步两个机器的时钟

traceroute 可跟踪从计算机到服务器的路由

#### 移动IP

对于移动主机，可采用先发给归属网络中的归属代理（归属地址），再由其转发给外地网络的移动主机本身。

- 归属代理——通常是连接在移动主机的归属网络上的路由器
- 外地代理——通常是连接在外地网络上的路由器，移动主机本身也可充当，此时转交地址叫作**同址转交地址**

##### 三个阶段

- 代理发现
  - 代理通告——路由器本身作为网络的代理时，可以使用ICMP路由器通告报文宣布自己连接到该网络的同时，附带一个代理通告，内容可以包括转交地址可选列表等
  - 代理询问——移动主机连接到新网络时如果没有收到代理通告，可以发起代理询问（同样也是使用ICMP的路由器询问分组）

- 登记

  移动主机将**登记请求**发送给外地代理后可以登记它的转交地址，同时外地代理也会向主机的归属代理登记，此时实现了归属代理和移动代理之间的互通；**登记回答**则是由归属代理发给外地代理后再转交给主机。

- 数据传送

  远程主机→归属代理→外地代理→移动主机           移动主机→远程主机

##### 低效情况

- 两次穿越——移动主机实际与远程主机处于同一网络时，远程主机的数据要先去归属网络再返回自己的网络，穿越了两次因特网
- 三角路由选择——远程主机到移动主机的分组走了三角形的两个边

##### 解决方案

归属代理第一次收到远程书记的数据时就应该向其返回转交地址，即发送一个**更新绑定分组**，使远程主机以后的数据都直接发到转交地址去；移动主机再次移动的话，归属代理要向远程主机发送**告警分组**。

#### 单播路由选择协议

##### 自治系统（autonomous system，AS）

一个管理机构管辖下的一组网络和路由器。其内部的路由选择称为**域内路由选择**（可以多个），AS之间的路由选择称为**域间路由选择**（只能一种）。

|           名称           | 路由选择协议类型 | 域内/域间 |
| :----------------------: | :--------------: | :-------: |
|   RIP（路由信息协议）    |   距离向量协议   |   域内    |
| OSPF（开放最短路径优先） |   链路状态协议   |   域内    |
|   BGP（边界网关协议）    |   路径向量协议   |   域间    |

##### 距离向量协议

Bellman-Ford算法

基本思想——邻居节点到目的节点的距离分别加上源节点到各个邻居节点的距离，取最小值（动态规划）

该类型协议的缺点是**故障消息传播速度慢**，典型场景有二节点循环，可才去的措施有**定义无穷大**、**分割范围**、**毒性逆转**。

##### 链路状态协议

Dijkstra算法

每个节点都了解网络中的拓扑，包括节点和他们之间的连线

每个节点创建**链路状态分组（LSP）**，之后向其他节点散发LSP，各个节点形成最短路径树后基于树结构计算路由表。

OSPF

##### 路径向量协议

给出通往各个AS的路径和代价，以及各个AS中包含了哪些网络。

自治系统类型

残桩AS——只有一个连接可到达另一个AS

多归属AS——可以作为信源或信宿连接到多个AS，但只能接收或发送，不能够转交

转接AS——是允许穿越通信量的多归属AS

IGMP（网际组管理协议，Internet Group Management Protocol）

——负责收集和解释一个网络中的组成员信息

# 3 运输层

## 3.1 基础

### 端口

|   端口   |    范围     |          区别           |
| :------: | :---------: | :---------------------: |
| 熟知端口 |   0~1023    |     ICANN指派和控制     |
| 注册端口 | 1024~49151  | 需要在ICANN注册以免重复 |
| 动态端口 | 49152~65535 |        临时可选         |

UDP（用户数据包协议）、TCP（传输控制协议）、SCTP（流控制传输协议）

### 协议

#### 简单协议

无连接——没有流量控制和差错控制

停止等待协议——一个分组一个确认，有限时间内收到确认后发下一个，否则重发

返回N协议（GBN）——未得到确认就发下一个分组，确认超时之后发送所有待确认的分组（发送窗口大小小于2^m，接收窗口大小始终为1）

选择重传协议——只重传真正丢失的分组

双向协议——返回数据的同时捎带自己的请求数据

#### UDP（用户数据报，User Datagram Protocol）

- 面向报文，不拆分不合并，每次接收到的都是完整的报文，即有边界

没有流量控制，收到分组之后也没有确认；但有一定程度上的差错控制，会在检测出差错之后（检验和）丢弃分组。

不可能发送数据流，所以短报文（长度小于65507字节）才会使用UDP。

可用于简单请求-响应的进程，以及具有内部流量控制、差错控制机制的进程（如简单文件传送协议TFTP），多播，管理进程，路由选择更新协议，实时应用场景。

#### TCP

- 面向字节，无边界

###### 三次连接

客户发送第一个报文段（SYN报文段），其作用是同步序号——不携带任何数据，但需要消耗掉一个序号

服务器发送第二个报文段（SYN+ACK），给出期望收到的下一个序号，同时定义**接收窗口的大小(rwnd)**——不携带任何数据，但需要消耗掉一个序号

客户发送第三个报文段（ACK），定义**服务器的窗口大小**——不携带数据的话不消耗序号，一般不携带

###### 两次连接

两个TCP同时向对方发送 SYN+ACK 报文段

###### SYN 洪泛攻击

方式：短时间伪造多个SYN报文段发送给服务器，造成服务器耗尽资源而不能接受正常合法的请求，是**拒绝服务攻击**的一种。

解决方案：强制限定指定时间内的请求次数，或者使用Cookie推迟资源的分配，直至证实连接请求来自合法的IP地址。

###### 三次握手关闭连接（四次握手可以实现半关闭）

类似于三次连接，只不过是FIN报文

该部分包括：常见情况、同时连接、同时关闭、拒绝连接、异常终止连接  几种情况

##### 流量控制——调整发送窗口大小

![image-20220817133535619](imgs\TCP&IP\image-20220817133535619.png)

步骤④即是我们重点讨论的环节：发送方运输层根据接收方的 **接收窗口大小（rwnd）** 信息调整发送窗口大小，而接收窗口大小取决于发送方的发送速度和接收方应用层的处理进度。

###### 糊涂窗口综合症

发送方应用程序产生数据很慢或者接收方消耗数据很慢或者二者兼具 所导致。

①发送方产生数据很慢——采用Nagle算法（待发送的数据量累积到一定程度再发送）

②接收方产生数据很慢——采用Clark算法（有数据到达就发送确认，但如果缓存空间不多的话就宣布rwnd为0）、推迟确认（直到缓存有足够空间再发送确认）

##### 差错控制

###### 常用规则

1. 仅**仅有一个**按序到达的报文段**未被确认**，接收方就**推迟**发送**确认**报文段（推迟有一定的时间限制，通常为500ms）。
2. 期望的序号报文已到达，但上一个未确认，则**立即确认上一个**（不能有两个以上未确认）。
3. 比期望**序号大**的报文到达时**立即发出确认**，是的期望序号报文**重传**。
4. 上一条中间**丢失的报文到达后**，发送ACK报文**宣布下一个期望的序号**。
5. **重复**的报文到达后，丢弃该报文并**立即发送确认**，指出下一个期望的报文。

###### RTO（重传超时，retransmission time-out）

——计时时间到时，TCP发送队列中最前面的报文段，并重启计时器（发送之后开始计时，如果一直没有收到确认，过了RTO就重传）

###### 快重传

——如果有针对某个报文段3个重复的确认，那下一个报文段将立即重传，不管计时器有没有超时。

##### 拥塞控制

——通过拥塞窗口大小（cwnd）决定，结合之前的流量控制，实际发送方的发送窗口大小取 min（rwnd, cwnd）

###### 慢开始

——按指数规律增大窗口大小，直到达到门限（指数是相对到达**ACK的数目**而言的）

（不考虑推迟ACK（即每个报文段被单独确认）等情况的话，窗口大小的变化规律是1,2,4,8...实际如果每两个报文段使用一个确认，窗口的增加趋势一般是1.5的乘方）

###### 拥塞避免

——达到门限之后窗口大小按**加法规律增加**（加法是相对**RTT**而言的，即往返时间）

###### 拥塞检测

——检测到拥塞后窗口需要乘法减小，即**回到最小值1**，而**门限值**则降为**当前窗口大小的一半**（慢恢复）

这里会分情况：

①如果是计时器超时导致重传，那么大概率是网络拥塞了，此时cwnd按上面所说设为最小值1（到最开始的慢启动阶段）；

②如果是3次ACK导致的快重传，那网络拥塞的概率比较小，此时cwnd设置问门限值即可（直接进入拥塞避免阶段）。

###### 计时器

- 重传超时 RTO——是由往返时间RTT计算得到的。由于RTT测量有很大偏差，会对其多次结果进行平滑处理，并计算其之间的偏差，最终表示形式为
  - RTO=RTTS+4RTTD，其中RTTS是平滑RTT，RTTD是偏差值。
  - Karn算法——TCP在计算新的RTO时不考虑重传报文段的RTT
  - 指数退避——发生重传之后RTO变为原来的2倍

- 持续计时器——用于解决**宣布窗口大小非0确认丢失造成的死锁**，发送方按照持续计时器的时间发送**探测报文**，没有回应则计时器的值加倍，直至达到门限（通常为60s）

- 保活计时器——用于连接建立之后**客户长时间静默**的情况，超过两小时服务器没有接收到请求就会想客户发送**探测报文**，若连续10个都没有回应则**终止连接**

#### STCP（流控制传输协议）

- 面向报文，有边界

一个**关联（association）**可以包含多重流，某一个流被阻塞时其他流可以继续交付数据。关联相当于TCP中的连接这个概念。

多重归属，即客户和服务器有多个IP地址连接到不同网络，当前路径故障时可启用其他路径，但每一时刻只有一个路径在工作

用**传输序号（TSN）**对数据块进行编号实现数据传输，同一关联中的不同流用**流标识符（SI）**区分，同一流当中的不同数据块用**流序号（SSN）**区分

TCP中一个**报文段**包含多个字节，实现传输数据和控制信息；SCTP中**分组**中可以有控制块和数据块，与报文段作用类似。分组中所包含的若干个块可以属于不同的流。

确认号是针对数据块的。

关联建立

四次握手

DHCP

客户端使用UDP端口68（熟知端口），服务器使用UDP端口67（熟知端口）。

- 同网络时客户端使用全0的源地址和全1的目的地址，服务器用广播或者单播报文响应。
- 不同网络时由某台主机作为中继代理，对地址请求和响应进行转发。

DHCP要求UDP使用检验和，并使用计时器和重传策略来解决丢包问题。

静态地址中没有与物理地址对应的IP时就在IP地址池中分配一个临时IP地址，并将其记录到动态数据库中，其使用是有时间限制的。