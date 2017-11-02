![sample](https://i.imgur.com/OJ2qtWT.png)
Create an XML file that represents the button states? Create an XML file that represents each button state? No more. With Customizable Buttons for Android you can easily create custom buttons for your app!

**NOTE:** This is a work in progress and is not recommended to use in your project yet. Documentation and plugin repository will be added when the project reaches a more mature stage.

<br><br>


<p align="center">
  <img src="https://i.imgur.com/Ypw9tGn.gif">
</p>

## Basic Example

In this basic example you can see how to easily create a custom button with ripple effect. Check the wiki (soon) for more examples.

### Code:
```XML
<com.github.gabrielgouv.customizablebuttons.CustomizableButton
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="0.5"
        app:cb_text="Normal / Enabled"
        app:cb_textColor="#DAEBF2"
        app:cb_textAllCaps="true"
        app:cb_elevation="4dp"
        app:cb_useRippleEffect="true"
        app:cb_rippleColor="#012C40"
        app:cb_rippleOpacity="1"
        app:cb_backgroundColor="#00708C"
        app:cb_backgroundColorPressed="#012C40"
        app:cb_borderColor="#012C40"
        app:cb_borderThickness="2dp"
        app:cb_borderRadius="30dp" />
```

Use `android:enabled="false"` or `app:cb_enabled="false"` to set your button disabled. You can check the wiki (soon) for more information about attributes.

### Result:
![sample](https://i.imgur.com/AgpE30d.gif)
