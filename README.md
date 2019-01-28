# ![Karumi logo][karumilogo] Kata SuperHeroes with Jetpack in Kotlin [![Build Status](https://travis-ci.org/Karumi/KataSuperHeroesJetpack.svg?branch=master)](https://travis-ci.org/Karumi/KataSuperHeroesJetpack)

- We are here to practice the usage of [Architecture Jetpack components][jetpackComponents].
- We are going to use [Kotlin][kotlin].
- We are going to practice pair programming.

---

## Getting started

This repository contains an Android application to show Super Heroes information:

![ApplicationScreencast][applicationScreencast]

This Application is based on three Activities:

* ``MainActivity`` showing a list of super heroes with name, photo and a special badge if it is part of the Avengers Team.

![MainActivity screenhot][mainActivityScreenshot]

- ``SuperHeroDetailActivity`` showing detailed information about a super hero like his or her name, photo and description.

![SuperHeroDetailActivity screenshot][superHeroDetailActivityScreenshot]

- ``EditSuperHeroActivity``: Display options to change some attributes of a superhero like their Avengers badge, their name and their description.

![EditSuperHeroActivity screenshot][editSuperHeroActivityScreenshot]

The application architecture, dependencies and configuration is ready to just start migrating code to Jetpack.

To verify the correct behaviour of your code you can execute:

```shell
./gradlew executeScreenshotTests
```

To be able to get a deterministic test scenario all our tests will be executed on the same emulated device. Run the following to create an emulator like the one we used to get all the reference screenshots:

```shell
./script/start_emulator.sh
```

## Tasks

Your task as Android Developer is to **migrate the application to Jetpack components**. You will do so step by step and always using tests to verify everything is still working.

**This repository is ready to build the application, pass the checkstyle and your tests in Travis-CI environments.**

Our recommendation for this exercise is:

- Before starting
  1. Fork this repository.
  2. Checkout `kata-jetpack` branch.
  3. Execute the application, explore it manually and make yourself familiar with the code.
  4. Execute `MainActivityTest` and `SuperHeroDetailActivityTest` to see tests run.
- Feel free to migrate at your own pace and order but if you want to make it a bit easier, follow this order:
   1. [Lifecycle][lifecycle]
   2. [Room][room]
   3. [DataBinding][data_binding], [LiveData][livedata] and [ViewModel][viewmodel]
   4. [Paging][paging]
   5. [Navigation][navigation]
   6. [WorkManager][workmanager]

## Considerations

If you get stuck, `master` branch contains the finished kata using all architecture components. There are also tags for every single new component following the above order.

---

## Documentation

There are some links which can be useful to finish these tasks:

- [Screenshot Kata in Kotlin][kataScreenshotKotlin]
- [Android Room with a View - Codelab][androidViewWithAViewCodelab]
- [Android Data Binding - Codelab][androidDataBindingCodelab]
- [Android Paging - Codelab][androidPagingCodelab]
- [Android lifecycle-aware components - Codelab][androidLifecycleCodelab]
- [Background Work with WorkManager - Codelab][workManagerCodelab]

Data provided by Marvel. © 2019 MARVEL

## License

Copyright 2019 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[kotlin]: https://kotlinlang.org/
[kodein]: https://github.com/SalomonBrys/Kodein
[jetpackComponents]: https://developer.android.com/jetpack/
[applicationScreencast]: ./art/ApplicationScreencast.gif
[mainActivityScreenshot]: ./art/main_activity.png
[superHeroDetailActivityScreenshot]: ./art/detail_activity.png
[editSuperHeroActivityScreenshot]: ./art/edit_activity.png
[room]: https://developer.android.com/topic/libraries/architecture/room
[data_binding]: https://developer.android.com/topic/libraries/data-binding/
[lifecycle]: https://developer.android.com/topic/libraries/architecture/lifecycle
[livedata]: https://developer.android.com/topic/libraries/architecture/livedata
[navigation]: https://developer.android.com/topic/libraries/architecture/navigation.html
[paging]: https://developer.android.com/topic/libraries/architecture/paging/
[viewmodel]: https://developer.android.com/topic/libraries/architecture/viewmodel
[workmanager]: https://developer.android.com/topic/libraries/architecture/workmanager
[kataScreenshotKotlin]: https://github.com/Karumi/KataScreenshotKotlin
[androidViewWithAViewCodelab]: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0
[androidDataBindingCodelab]: https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/index.html#0
[androidPagingCodelab]: https://codelabs.developers.google.com/codelabs/android-paging/index.html#0
[androidLifecycleCodelab]: https://codelabs.developers.google.com/codelabs/android-lifecycles/index.html#0
[workManagerCodelab]: https://codelabs.developers.google.com/codelabs/android-workmanager-kt/index.html#0