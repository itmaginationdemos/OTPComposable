# OTPComposable
This is a small library/view for anyone who is not in the mode to create yet another OTP screen from scratch.


## Integration
Follow these steps:

Step 1. Add the Jitpack repository in your root build.gradle at the end of repositories:
```kotlin
allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}
```

Step 2. Add the dependency
```kotlin
dependencies {
    implementation 'com.github.itmaginationdemos:OTPComposable:0.2'
}
```

## Using it
We made sure to make it as simple as possible.  
Bellow examples will include example which use every available property.  
Properties will be explained in the next section.

### Otp View with unknown Pin/Password
In cases where you will check if the Pin is correct only after the user entered it or in cases
where mobile client will never know the value of a correct pin you should use this Composable.  
This one is a bit more complex for you as you will have to handle the State changes (LCE). We did
try to help out as much as we could without hurting the customization options.

```kotlin
 val (pinValue, onPinValueChange) = remember { mutableStateOf("") }

OtpView(
  pin = pinValue,
  onPinChange = onPinValueChange, 
  onFullPin: (String) -> Unit,
  content: ContentCustomization,
  error: ErrorCustomization,
  loading: LoadingCustomization,
  scaffoldState: ScaffoldState,
  lce: LCE = LCE.Content,
)
```
### Otp View with preexisting Pin
This one is easy. You just pass us the pin you expect from user and a success lambda and you are
golden. Rest of the logic comes for free.

```kotlin
 val (pinValue, onPinValueChange) = remember { mutableStateOf("") }

ExistingOtpView(
  pin: String, 
  onPinChange: (String) -> Unit,
  expectedPin: String,
  onSuccess: () -> Unit,
  content: ContentCustomization,
  error: ErrorCustomization,
  scaffoldState: ScaffoldState
)
```

## Properties
### Basics
`Pin` - current pin entered by user  
`onPinChange` - action you want to take when user adds or removes a character (usually just save it)  
`expectedPin` (ExistingOtpView) - in case you know the pin in advance just put it here and 
all state changes are handled  
`onSuccess` (ExistingOtpView) - lambda to call when we determined the pin is correct  
`onFullPin` (OtpView) - lambda which is called when all digits are entered  
`scaffoldState` - needed for calling a Snackbar  
`lce` (OtpView) - lce, or Loading, Content, Error. More details in next chapter, but basically
these are the three possible state of this Composable

### ErrorCustomization
```kotlin
ErrorCustomization(
  val modifier: Modifier = Modifier,
  val snackMsg: String = "",
  val message: String = "Code is incorrect",
)
```
`modifier` - standard stuff  
`snackMsg` - if empty no Snackbar will be shown. In other case this will be the message in it  
`message` - message displayed bellow the inputs when wrong pin is entered. If empty still ocupies
space  

### ContentCustomization
```kotlin
ContentCustomization(
  val modifier: Modifier = Modifier,
  val color: Color = Color.Black,
  val digitCount: Int = 6,
  val type: DigitViewType = DigitViewType.Rounded(50),
  val digitSize: TextUnit = 24.sp,
  val containerSize: Dp = digitSize.value.dp * 2.2f,
)
```

`modifier` - standard stuff  
`color` - text color  
`digitCount` - number of places for pin, important for logic   
`digitSize` - font size of digit  
`containerSize` - size of the cell for one digit, default is 2.2 times the digit font size
`type` - see bellow

```kotlin
DigitViewType {
  object Underline : DigitViewType
  data class Rounded(@IntRange(from = 0, to = 50) val percentage: Int): DigitViewType
}
```

`Underline` - digit cell will have no borders just a line bellow the digit itself  
`Rounded` - digit cell with borders ranging from 0 to 50. 0 means it will be a square cell while
a 50 will mean it will be a circle one. And all other numbers in between are basically rounded 
corners.

### LoadingCustomization
```kotlin
LoadingCustomization(
  val modifier: Modifier = Modifier,
  val loadingMessage: String = "Loading...",
  val showMessage: Boolean = true,
  val showProgress: Boolean = true,
)
```
`modifier` - standard stuff  
`loadingMessage` - displayed message  
`showMessage` -  if false no text will be shown  
`showProgress` -  if false no loading indicator will be shown