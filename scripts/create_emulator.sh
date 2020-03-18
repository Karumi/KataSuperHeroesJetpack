#!/bin/bash

if $ANDROID_HOME/tools/android list avd | grep -q screenshot-test; then
    echo "There is an existing an emulator to run screenshot tests"
    exit 0;
fi

echo "Cleanup old SDCard..."
rm -rf sdcard.img

echo "Downloading the image to create the emulator..."
echo no | $ANDROID_HOME/tools/bin/sdkmanager "system-images;android-26;default;x86_64"
echo "Image downloaded!"

echo "Creating the emulator to run screenshot tests..."
echo no | $ANDROID_HOME/tools/bin/avdmanager --verbose create avd --force --name "screenshot-test" --abi default/x86_64 --package "system-images;android-26;default;x86_64" -c 1G
echo "Emulator created!"

echo "Editing emulator configuration..."
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cp $DIR/config.ini ~/.android/avd/screenshot-test.avd/config.ini
echo "Emulator edited!"
cat ~/.android/avd/screenshot-test.avd/config.ini