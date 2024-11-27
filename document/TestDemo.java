// 将 BigDecimal 乘以 100，并且去掉小数部分
BigDecimal percentage = number.multiply(new BigDecimal("100")).setScale(0, RoundingMode.DOWN);
        return percentage.toString() + "%";  // 拼接百分号