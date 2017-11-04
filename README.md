![sample](https://i.imgur.com/OJ2qtWT.png)

<p align="center">
  
  <a href="https://android-arsenal.com/api?level=14">
    <img src="https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat"
  alt="Gratipay"></a>
  
  <a href="https://travis-ci.org/GabrielGouv/Android-Customizable-Buttons">
    <img src="https://travis-ci.org/GabrielGouv/Android-Customizable-Buttons.svg?branch=master"
  alt="Gratipay"></a>
  
  <a href="https://opensource.org/licenses/MIT">
    <img src="https://img.shields.io/badge/License-MIT-yellow.svg"
  alt="Gratipay"></a>
  
  <a href="https://www.codacy.com/app/GabrielGouv/Android-Customizable-Buttons?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=GabrielGouv/Android-Customizable-Buttons&amp;utm_campaign=Badge_Grade">
  <img src="https://api.codacy.com/project/badge/Grade/20a52dd334204b64a361d313435e5a1c"/></a>
  
 </p>

<br>

Create an XML file that represents the button states? Create an XML file that represents each button state? No more. With Customizable Buttons for Android you can easily create custom buttons for your app!

**NOTE:** This is a work in progress and is not recommended to use in your project yet. The documentation and plugin repository will be added soon, when the project reaches a more mature stage.

<br><br>
<p align="center">
  <img src="https://i.imgur.com/Ypw9tGn.gif">
</p>

# Basic Example

In this basic example you can see how to easily create a custom button with ripple effect. Check the Wiki (soon) for more examples.

### XML:
```XML
<com.github.gabrielgouv.customizablebuttons.CustomizableButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
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

You can use `android:enabled="false"` or `app:cb_enabled="false"` to set your button disabled. You can check the [**Button XML Attributes**](https://github.com/GabrielGouv/Android-Customizable-Buttons/wiki/Button-XML-Attributes) for more information about attributes.

### Result:
![sample](https://i.imgur.com/AgpE30d.gif)

# License

```
MIT License

Copyright (c) 2017 João Gabriel Gouveia de Almeida

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

