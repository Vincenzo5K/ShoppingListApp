package com.example.shoppinglist;

public class ShoppingListItem
{
    private String name;
    private String price;
    private String imgUrl;
    private int id;
//    private boolean isExpanded;

    public ShoppingListItem(int id, String name, String price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
//        this.isExpanded = false;
    }

//    public boolean isExpanded() {
//        return isExpanded;
//    }
//
//    public void setExpanded(boolean expanded) {
//        isExpanded = expanded;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", id=" + id +
                ", " +
//                "isExpanded=" + isExpanded +
                '}';
    }
}
