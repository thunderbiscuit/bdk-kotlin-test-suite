# Readme
This repo is a collection of tests and easy to reuse pieces of code for bdk-jvm and bdk-android. Note that they don't aim to provide a full coverage of the bdk-jvm and bdk-android libraries; they're just useful for tinkering away with the API and trying out new ideas fast without requiring a full application.

You can run individual test classes using the `--tests` flag in Gradle like so:
```shell
# run all tests
./gradlew test

# run individual test class
./gradlew test --tests bdk.kotlin.testsuite.OfflineWallet
```

I currently have classes tinkering with:
- [x] Offline wallets
- [x] Online wallets
- [x] Multisig wallets
- [x] OP_RETURN
- [x] Partial signatures of PSBTs and multisig
- [x] List unspent UTXOs
