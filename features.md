# Features by version

v1.1
* help list of crypto + currencies
* add default and fallback values
* print currency symbol with prices
* add argument validation tests
* add TTL config for caching

v1.2
* Performance
  * Calculate % increase/decrease from one day to another
    * Example: `crypto gains -c bitcoin 2020-01-01 2021-01-01`
  * Compare performance of one coin to another
    * Example: `crypto gains -c bitcoin -c ethereum 2020-01-01 2021-01-01`
* Visualization
  * Graph for historical data
  * Example: `crypto history -d 1 -c bitcoin --graph`

v1.3
* Track historical prices on a monthly basis (e.g. Jan 1, Feb 1, Mar 1)

v1.4
* Refactor to use [Java wrapper](https://github.com/Philipinho/CoinGecko-Java) for CoinGecko API

v1.5
* Statistical modelling
  * TBD

v2.0
* Trading bot
  * Command to "buy" or "sell" at current time
  * Track gains/losses
