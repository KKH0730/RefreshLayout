# SimpleRefreshLayout
A simple refresh layout library   


# Design
All disign by myself

![refresh_layout](https://user-images.githubusercontent.com/66052075/104601438-98f60600-56bd-11eb-9926-df5e720a8d92.gif)



# Dependency   
- project build.gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

- App build.gradle
```
implementation 'com.github.KKH0730:RefreshLayout:1.0'
```



# Usage
- Use it in your layout xml.
You have to surround layout 
```
<seno.library.refreshlayout.SimpleRefreshLayout
     android:id="@+id/refreshLayout"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:orientation="vertical">
				
     ...
				
</seno.library.refreshlayout.SimpleRefreshLayout>
```

- Get instance and use it.
```
refreshLayout.setRefreshListener(object: OnRefreshListener{
    override fun onRefreshed() {
		//start refresh    
    ...
		
		//when you want finish refresh
    refreshLayout.setRefreshing(false);
		
    }
})
```


