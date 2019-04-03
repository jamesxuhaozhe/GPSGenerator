echo "start building" 

./gradlew clean assembleDebug -x lint

echo "Start installing" 

adb install -r $(pwd)/app/build/outputs/apk/debug/app-debug.apk
