这是一个针对Android Tv Launcher页的recyclerView

下面是效果图：


![img.png](https://github.com/songwenju/CustomTvRecyclerView/blob/master/raw/master/screenshots/img.png)

gif图：
![tvRecycler.gif](https://github.com/songwenju/CustomTvRecyclerView/blob/master/raw/master/screenshots/tvRecycler.gif)

封装了RecyclerView实现了一下一些功能:
1.响应五向键，按下五向键的上下左右会跟着移动，并获得焦点，在获得焦点时会抬高
2.在鼠标hover在条目上时会获得焦点。
3.添加了条目的点击和长按事件。
4.添加了是否第一个可见条目和是否是最后一个可见条目的方法。
5.在item获得焦点时和失去焦点时，这里有相应的回调方法。

