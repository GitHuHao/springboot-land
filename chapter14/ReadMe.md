# couchbase
```text
对标 memcache 
一、redis

1 Redis数据库完全在内存中，因此处理速度非常快，每秒能执行约11万集合，每秒约81000+条记录；

2 Redis的数据能确保一致性——所有Redis操作是原子性（Atomicity，意味着操作的不可再分，要么执行要么不执行）的，这保证了如果两个客户端同时访问的Redis服务器将获得更新后的值。

3 通过定时快照（snapshot）和基于语句的追加（AppendOnlyFile，aof）两种方式，redis可以支持数据持久化——将内存中的数据存储到磁盘上，方便在宕机等突发情况下快速恢复。

优点：

1. 非常丰富的数据结构；

2. Redis提供了事务的功能，可以保证一串 命令的原子性，中间不会被任何操作打断；

3. 数据存在内存中，读写非常的高速，可以达到10w/s的频率。

缺点：

1. Redis3.0后才出来官方的集群方案，但仍存在一些架构上的问题（出处）；

2. 持久化功能体验不佳——通过快照方法实现的话，需要每隔一段时间将整个数据库的数据写到磁盘上，代价非常高；而aof方法只追踪变化的数据，类似于mysql的binlog方法，但追加log可能过大，同时所有操作均要重新执行一遍，恢复速度慢；

3. 由于是内存数据库，所以，单台机器，存储的数据量，跟机器本身的内存大小。虽然redis本身有key过期策略，但是还是需要提前预估和节约内存。如果内存增长过快，需要定期删除数据。

适用场景：

适用于数据变化快且数据库大小可遇见（适合内存容量）的应用程序。

couchbase

Couchbase Server 是个面向文档的数据库（其所用的技术来自于Apache CouchDB项目），能够实现水平伸缩，并且对于数据的读写来说都能提供低延迟的访问（这要归功于Membase技术）。

1.特点

1.1 数据格式

Couchbase 跟 MongoDB 一样都是面向文档的数据库，不过在往 Couchbase 插入数据前，需要先建立 bucket —— 可以把它理解为“库”或“表”。

因为 Couchbase 数据基于 Bucket 而导致缺乏表结构的逻辑，故如果需要查询数据，得先建立 view（跟RDBMS的视图不同，view是将数据转换为特定格式结构的数据形式如JSON）来执行。

Bucket的意义 —— 在于将数据进行分隔，比如：任何 view 就是基于一个 Bucket 的，仅对 Bucket 内的数据进行处理。一个server上可以有多个Bucket，每个Bucket的存储类型、内容占用、数据复制数量等，都需要分别指定。从这个意义上看，每个Bucket都相当于一个独立的实例。在集群状态下，我们需要对server进行集群设置，Bucket只侧重数据的保管。

每当views建立时， 就会建立indexes， index的更新和以往的数据库索引更新区别很大。 比如现在有1W数据，更新了200条，索引只需要更新200条，而不需要更新所有数据，map/reduce功能基于index的懒更新行为，大大得益。

要留意的是，对于所有文件，couchbase 都会建立一个额外的 56byte 的 metadata，这个 metadata 功能之一就是表明数据状态，是否活动在内存中。同时文件的 key 也作为标识符和 metadata 一起长期活动在内存中。

1.2 性能

couchbase 的精髓就在于依赖内存最大化降低硬盘I/O对吞吐量的负面影响，所以其读写速度非常快，可以达到亚毫秒级的响应。

couchbase在对数据进行增删时会先体现在内存中，而不会立刻体现在硬盘上，从内存的修改到硬盘的修改这一步骤是由 couchbase 自动完成，等待执行的硬盘操作会以write queue的形式排队等待执行，也正是通过这个方法，硬盘的I/O效率在 write queue 满之前是不会影响 couchbase 的吞吐效率的。

鉴于内存资源肯定远远少于硬盘资源，所以如果数据量小，那么全部数据都放在内存上自然是最优选择，这时候couchbase的效率也是异常高。

但是数据量大的时候过多的数据就会被放在硬盘之中。当然，最终所有数据都会写入硬盘，不过有些频繁使用的数据提前放在内存中自然会提高效率。

1.3 持久化

其前身之一 memcached 是完全不支持持久化的，而 Couchbase 添加了对异步持久化的支持：

Couchbase提供两种核心类型的buckets —— Couchbase 类型和 Memcached 类型。其中 Couchbase 类型提供了高可用和动态重配置的分布式数据存储，提供持久化存储和复制服务。

Couchbase bucket 具有持久性 —— 数据单元异步从内存写往磁盘，防范服务重启或较小的故障发生时数据丢失。持久性属性是在 bucket 级设置的。

Couchbase 群集所有点都是对等的，只是在创建群或者加入集群时需要指定一个主节点，一旦结点成功加入集群，所有的结点对等。

对等网的优点是，集群中的任何节点失效，集群对外提供服务完全不会中断，只是集群的容量受影响。

由于 couchbase 是对等网集群，所有的节点都可以同时对客户端提供服务，这就需要有方法把集群的节点信息暴露给客户端，couchbase 提供了一套机制，客户端可以获取所有节点的状态以及节点的变动，由客户端根据集群的当前状态计算 key 所在的位置。

3. 优缺点

优势

1. 高并发性，高灵活性，高拓展性，容错性好；

2. 以 vBucket 的概念实现更理想化的自动分片以及动态扩容（了解更多）；

缺点

1. Couchbase 的存储方式为 Key/Value，但 Value 的类型很为单一，不支持数组。另外也不会自动创建doc id，需要为每一文档指定一个用于存储的 Document Indentifer；

2. 各种组件拼接而成，都是c++实现，导致复杂度过高，遇到奇怪的性能问题排查比较困难，（中文）文档比较欠缺；

3. 采用缓存全部key的策略，需要大量内存。节点宕机时 failover 过程有不可用时间，并且有部分数据丢失的可能，在高负载系统上有假死现象；

4. 逐渐倾向于闭源，社区版本（免费，但不提供官方维护升级）和商业版本之间差距比较大。

适用场景

1. 适合对读写速度要求较高，但服务器负荷和内存花销可遇见的需求；

2. 需要支持 memcached 协议的需求。
```
