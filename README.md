# Crypto CLI ⚡️

Ever felt the pressing need to lookup crypto data right from your shell? Now you can!

### Installation
Note: requires Java 11+
```bash
git clone https://github.com/acrenwelge/crypto-cli
cd ./crypto-cli
gradle build
cp ./app/build/libs/app.jar /usr/local/bin/crypto.jar # or another location on your $PATH
echo "alias crypto='java -jar /usr/local/bin/crypto.jar'" >> ~/.bashrc
alias crypto='java -jar /usr/local/bin/crypto.jar'
```

### Usage

> Please note: there is currently a **rate limit of 50 calls / minute** for the free API being used

👀 Search for a crypto
```bash
crypto search bit
```

ℹ️ Lookup info on a cryptocurrency
```bash
crypto --coin bitcoin
```

💰 Get price info only
```bash
crypto price --coin bitcoin
```

💶 Get price info for multiple coins in multiple currencies
```bash
crypto price -c bitcoin -c ethereum -c litecoin -cur USD -cur EUR
```

📈 Get price history of bitcoin for last 10 days
```bash
crypto history -c bitcoin -d 10
```

📖 Get USD price of bitcoin on May 1, 2015
```bash
crypto price -c bitcoin --date 2015-05-01
```

⏱ Watch the price (default: refresh every 15 seconds)
```bash
crypto price -c bitcoin --watch
crypto price -c bitcoin -w -i 10 -s 1 # interval of 10 seconds, stop after 1 minute
```

### Config
⚙️ Set default crypto and currency
```bash
crypto config -c bitcoin
crypto config -cur USD
crypto config # displays current defaults if no args set
```
> Crypto coin must be its `id`. Currency must follow [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217).

### Caching
A list of coins to search will be cached in `$HOME/.crypto/coins.json` for quick searching and lookup. By default, a [TTL](https://en.wikipedia.org/wiki/Time_to_live) value of 7 days will be used.

The default coin and currency to use will be stored in `$HOME/.crypto/defaults.properties`. If this file exists, these values will be used unless overridden with command line arguments. If no file exists and no command line arguments are provided, the program reverts to bitcoin and USD defaults, respectively.

### Built with...
* [Java 11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html)
* [Gradle 7.2](https://docs.gradle.org/current/userguide/userguide.html)
* [Coingecko API](https://www.coingecko.com/en/api/documentation?)
* [picocli](https://picocli.info/)
* [Google Gson](https://github.com/google/gson/blob/master/UserGuide.md)
