package br.com.at_produtos.Services;

public class ExchangeRateResponse {

    private UsdBrl usdBRL;

    public UsdBrl getUsdBrl() {
        return usdBRL;
    }

    public void setUsdBrl(UsdBrl usdBRL) {
        this.usdBRL = usdBRL;
    }

    public static class UsdBrl {
        private double price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
