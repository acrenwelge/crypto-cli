# Crypto CLI

### Installation
```bash
git clone https://github.com/acrenwelge/crypto-cli
gradle build
cp build/libs/app.jar /bin/crypto.jar # or another location on your $PATH
echo "alias crypto='java -jar /bin/crypto.jar'" > ~/.bashrc
alias crypto='java -jar /bin/crypto.jar'
```

### Usage

> Please note: there is currently a rate limit of 50 calls / minute for the free API being used

✅ Search for a crypto
```bash
crypto search bit
```

✅ Lookup info on a cryptocurrency
```bash
crypto --coin bitcoin
```

✅ Get price info only
```bash
crypto price --coin bitcoin
```

✅ Get price info for multiple coins in multiple currencies
```bash
crypto price -c bitcoin ethereum -c litecoin -cur usd -cur eur
```

✅ Get price history of bitcoin for last 10 days
```bash
crypto history -c bitcoin -d 10
```

🤨 Get USD price of bitcoin on May 1, 2015
```bash
crypto price -c bitcoin --date 5-1-2015
```

Watch the price (default: refresh every 30 seconds)
```bash
crypto price -c bitcoin --watch
```

### Built with...
* Java
* Gradle
* [Coingecko API](https://www.coingecko.com/en/api/documentation?)
* [picocli](https://picocli.info/)
* [Google Gson](https://github.com/google/gson/blob/master/UserGuide.md)