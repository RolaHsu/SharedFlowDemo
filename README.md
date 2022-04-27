# SharefFlowDemo
Question of SharedFlow 

```kotlin
dataSource.sharedQueueFlow.onEach { list ->
    logMessage("update queue: $list")

    dataSource.sharedCurrentIndexFlow.first().let { index ->
        logMessage("update index: $index")
    }

}.launchIn(lifecycleScope)
```
### 前情提要
sharedQueueFlow、sharedCurrentIndexFlow 是 hot flow

### 需求
觀察 queue 的變動，去做對應的事

### 實際行為
只有第一次會觀察 queue 的變動，之後的更新皆不會接收，反而是會去觀察 current index 的改動

附上 log message :
```Java
---- update queue ----
update queue: [A, B, ABC, D]

---- update queue ----
---- update queue ----

---- update index ----
update index: 2
update queue: [ccc, B, ABC, aaa]

---- update queue ----
---- update queue ----
```




