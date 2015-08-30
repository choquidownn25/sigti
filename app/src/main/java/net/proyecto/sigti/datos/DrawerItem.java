package net.proyecto.sigti.datos;

/**
 * Created by choqu_000 on 28/07/2015.
 */
public class DrawerItem {

    //Atributos
    private String name;
    private int iconId;

    //Constructor poliformismo
    public DrawerItem(String name, int iconId){
        this.name=name;
        this.iconId=iconId;
    }

    //Encapsulamiento / Propiedad
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

}
