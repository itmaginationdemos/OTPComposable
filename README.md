# Compose One Time Password View
This is a small library/view for anyone who is not in the mood to create yet another OTP screen from scratch.


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
We made sure to make it as simple as possible. Bellow example includes every customization option currently possible.

```kotlin
 val (pinValue, onPinValueChange) = remember { mutableStateOf("") }

OtpView(
  pin = pinValue,
  onPinChange = onPinValueChange,
  expectedPin = "123456",
  onSuccess = { Log.d("OTP", "SUCCESS") },
  modifier = Modifier.padding(8.dp),
  digitSize: TextUnit = 24.sp,
  containerSize: Dp = digitSize.value.dp * 2.2f,
  digitCount: Int = 6,
  color: Color = MaterialTheme.colors.onBackground,
  type = DigitViewType.Rounded(50),
  context = context,
  errorModifier = Modifier.padding(8.dp)
  errorToastMsg = "Wrong code entered",
  errorMessage: String = "Code is incorrect"
)
```