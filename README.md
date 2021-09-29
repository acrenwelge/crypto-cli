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