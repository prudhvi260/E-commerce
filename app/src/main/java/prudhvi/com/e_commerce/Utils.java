package prudhvi.com.e_commerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;
import prudhvi.com.e_commerce.Model.Reviev;

public class Utils {
    private static final  String tag="Utils";
    public static final String Database_name="fake_database";
    private static int ID=0;
    private static int ORDER_ID=0;
    private Context context;
    public static int getOrderId()
    {
        ORDER_ID++;
        return ORDER_ID;
    }
    public Utils(Context context)
    {
      this.context=context;
    }
    public static int getID()
    {
        ID++;
        return ID;
    }
    public void updateTheRate(GroceryItem item,int newRate)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>items=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if (null!=items)
        {
            ArrayList<GroceryItem>newItems=new ArrayList<>();
            for (GroceryItem i: items){
                if (i.getId()==item.getId())
                {
                    i.setRate(newRate);
                }
                newItems.add(i);
            }
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("allItems",gson.toJson(newItems));
            editor.commit();
        }
    }
    public void addItemtoCart(int id)
    {

        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer>cartItems=gson.fromJson(sharedPreferences.getString("cartItems",null),type);
        if (null==cartItems)
        {
            cartItems=new ArrayList<>();
        }
        cartItems.add(id);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cartItems",gson.toJson(cartItems));
        editor.commit();

    }
    public boolean addReview(Reviev reviev)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>items=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if (null!=items){
            ArrayList<GroceryItem> newItems=new ArrayList<>();
            for (GroceryItem item:items){
                if(item.getId()==reviev.getGroceryitemid()) {
                    ArrayList<Reviev> revievs=item.getRevievs();
                    revievs.add(reviev);
                    item.setRevievs(revievs);
                }
                newItems.add(item);
            }
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("allItems",gson.toJson(newItems));
            editor.commit();
            return true;
        }
        return false;
    }
    public ArrayList<Reviev> getReviewsForItem(int id){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>items=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if(null!=items)
        {
            for (GroceryItem item:items){
                if(item.getId()==id)
                {
                    return item.getRevievs();
                }
            }
        }
        return null;
    }

    public void initdatabase()
    {
        Log.d(tag,"initdatabase: Started");
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>possibleitems=gson.fromJson(sharedPreferences.getString("allItems",""),type);//type convverting jason string into Arraylist<FroceryItem>
        if(null==possibleitems)
        {
            initAllItems();
        }
        
    }
    public ArrayList<GroceryItem> getAllitems()
    {
        Log.d(tag,"getAllItems:called");
        Gson gson=new Gson();
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allitems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        return allitems;
    }
    private void initAllItems()
    {
        Log.d(tag,"initAllItems : Started");
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        ArrayList<GroceryItem>allItems=new ArrayList<GroceryItem>();
        GroceryItem icecream=new GroceryItem("icecream","produced of fresh milk",
                "http://bloximages.newyork1.vip.townnews.com/virginislandsdailynews.com/content/tncms/assets/v3/editorial/c/f9/cf961d35-5e81-58c5-ae8b-63c27b2020ef/5b57bcd072ed3.image.jpg",
                "food",15,2.5);
        allItems.add(new GroceryItem("cheese","best cheese possible","https://amp.businessinsider.com/images/5b8592ba89c8a1d6218b4a36-750-563.jpg",
                "food",30,45.5));
        allItems.add(new GroceryItem("cucumber","it is fresh","https://cdn1.medicalnewstoday.com/content/images/articles/283/283006/cucumber-slices.jpg",
                "vegetables",10,40));
        allItems.add(new GroceryItem("coco cola","it is tasty drink","https://www.myamericanmarket.com/873-large_default/coco-cola-classic.jpg",
                "drinks",100,40));
        allItems.add(new GroceryItem("spaghetti","it is easy to cook meel","https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/barilla-rotini-pasta-1527694739.jpg",
                "food",40,600));
        allItems.add(new GroceryItem("chicken nugget","usually enough for 4 persons","https://www.seriouseats.com/images/2014/01/20140122-taste-test-nuggets-banquet.jpg",
                "food",10,20));
        allItems.add(new GroceryItem("clear shampoo","no hair fall","https://100comments.com/wp-content/uploads/2018/02/Untitled-10-3.jpg",
                "shampoo",42,100));
        allItems.add(new GroceryItem("Axe","hot and sexy","http://pics.drugstore.com/prodimg/519930/900.jpg","hygiene",20,40));
        allItems.add(new GroceryItem("Kelenex","soft and famous","https://images-na.ssl-images-amazon.com/images/I/91ZyGoGBMAL._SY355_.jpg","hygiene",10,60));
        allItems.add(icecream);
        String finalString=gson.toJson(allItems);
        editor.putString("allItems",finalString);
        editor.commit();
    }
    public ArrayList<GroceryItem> searchforitem(String text)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<GroceryItem>searchitems=new ArrayList<>();
        if(null!=allItems)
        {
            for(GroceryItem item:allItems)
            {
                if (item.getName().equalsIgnoreCase(text))
                {
                    searchitems.add(item);
                }
                String[] splittedString=item.getName().split(" ");
                for (int i=0;i<splittedString.length;i++)
                {
                    boolean doesExist=false;
                    for (GroceryItem searchitem:searchitems)
                    {
                        if (searchitem.equals(item)){
                            doesExist=true;
                        }
                    }
                    if(!doesExist){
                        searchitems.add(item);
                    }
                }
            }
        }
        return searchitems;
    }
    public ArrayList<String> getThreeCategories()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<String>catagories =new ArrayList<>();
        if(null!=allItems)
        {
            for (int i=0;i<allItems.size();i++)
            {
                if (catagories.size()<3)
                {
                    boolean doesExist=false;
                    for (String s:catagories){
                        if (allItems.get(i).getCategory().equals(s)){
                            doesExist=true;
                        }
                    }
                    if (!doesExist)
                    {
                        catagories.add(allItems.get(i).getCategory());
                    }
                }
            }
        }
        return catagories;
    }
    public ArrayList<String> getAllCategories()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<String>catagories =new ArrayList<>();
        if(null!=allItems)
        {
            for (int i=0;i<allItems.size();i++)
            {
                    boolean doesExist=false;
                    for (String s:catagories){
                        if (allItems.get(i).getCategory().equals(s)){
                            doesExist=true;
                        }
                    }
                    if (!doesExist)
                    {
                        catagories.add(allItems.get(i).getCategory());
                    }
            }
        }
       return catagories;
    }
    public ArrayList<GroceryItem>getItemsByCategory(String category)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<GroceryItem>newItems=new ArrayList<>();
        if (null!=allItems)
        {
            for (GroceryItem item:allItems){
                if (item.getCategory().equals(category)){
                    newItems.add(item);
                }
            }
        }
        return newItems;
    }
    public ArrayList<Integer>getCartItems()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer>CartItems=gson.fromJson(sharedPreferences.getString("cartItems",null),type);
        return CartItems;
    }
    public ArrayList<GroceryItem>getItemsByID(ArrayList<Integer>ids){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<GroceryItem>resultItems=new ArrayList<>();
        for (int id:ids)
        {
            if (null!=allItems)
            {
                for (GroceryItem item:allItems){
                    if (item.getId()==id){
                        resultItems.add(item);
                    }
                }
            }
        }

        return resultItems;
    }
    public ArrayList<Integer> deleteCartItem(GroceryItem item)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer>CartItems=gson.fromJson(sharedPreferences.getString("cartItems",null),type);
        ArrayList<Integer>newItems=new ArrayList<>();
        if (null!=CartItems){
           for (int i:CartItems){
               if (item.getId()!=i){
                   newItems.add(i);
               }
           }
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("cartItems",gson.toJson(newItems));
            editor.commit();
        }
        return newItems;
    }
    public void removeCartItems(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("cartItems");
        editor.apply();
    }
    public void addpopularityPoint(ArrayList<Integer>items){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        ArrayList<GroceryItem>newItems=new ArrayList<>();
        for (GroceryItem i:allItems){
            boolean doesExist=false;
            for (int j:items)
            {
                if (i.getId()==j)
                {
                    doesExist=true;
                }
            }
            if (doesExist){
                i.setPopularityPoint(i.getPopularityPoint()+1);
            }
            newItems.add(i);
        }
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("allItems",gson.toJson(newItems));
        editor.apply();
    }
    public void increaseUserPoint(GroceryItem item,int points)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(Database_name,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString("allItems",null),type);
        if (null!=allItems)
        {
            ArrayList<GroceryItem>newItems=new ArrayList<>();
            for (GroceryItem i:allItems){
                if (i.getId()==item.getId())
                {
                    i.setUserpoint(i.getUserpoint()+points);
                }
                newItems.add(i);
            }
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("allItems",gson.toJson(newItems));
            editor.commit();
        }
    }
}
