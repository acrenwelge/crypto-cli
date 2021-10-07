# Crypto CLI ⚡️

Ever felt the pressing need to lookup crypto data right from your shell? Now you can!

### Installation
```bash
git clone https://github.com/acrenwelge/crypto-cli
cd ./crypto-cli
gradle build
cp ./app/build/libs/app.jar /usr/local/bin/crypto.jar # or another location on your $PATH
echo "alias crypto='java -jar /usr/local/bin/crypto.jar'" >> ~/.bashrc
alias crypto='java -jar /usr/local/bin/crypto.jar'
```

### Usage

> Please note: there is currently a rate limit of 50 calls / minute for the free API being used

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
crypto price -c bitcoin -c ethereum -c litecoin -cur usd -cur eur
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

### Built with...
* Java
* Gradle
* [Coingecko API](https://www.coingecko.com/en/api/documentation?)
* [picocli](https://picocli.info/)
* [Google Gson](https://github.com/google/gson/blob/master/UserGuide.md)
