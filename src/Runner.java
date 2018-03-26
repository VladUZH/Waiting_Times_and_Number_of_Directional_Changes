/**
 */
public class Runner {
    public double extreme;
    public double deltaUp;
    public double deltaDown;
    public int type;
    public boolean initalized;
    public boolean absolute;

    public Runner(double threshUp, double threshDown, ATick aTick, int type, boolean absolute){
        extreme = aTick.price;
        this.type = type; deltaUp = threshUp; deltaDown = threshDown; initalized = true; this.absolute = absolute;
    }


    public int run(ATick aTick){

        if( aTick == null )
            return 0;

        if( !initalized ){
            initalized = true;
            extreme = aTick.price;
            return 0;
        }

        if (absolute){
            return absoluteRun(aTick);
        } else {
            return relativeRun(aTick);
        }


    }


    private int absoluteRun(ATick aTick){
        if( type == -1 ){
            if( (aTick.price - extreme) >= deltaUp ){
                type = 1;
                extreme = aTick.price;
                return 1;
            }
            if( aTick.price < extreme ){
                extreme = aTick.price;
                return 0;
            }
        }else if( type == 1 ){
            if( (aTick.price - extreme) <= -deltaDown ){
                type = -1;
                extreme = aTick.price;
                return -1;
            }
            if( aTick.price > extreme ){
                extreme = aTick.price;
                return 0;
            }
        }

        return 0;
    }

    private int relativeRun(ATick aTick){
        if( type == -1 ){
            if( (aTick.price - extreme) / extreme >= deltaUp ){
                type = 1;
                extreme = aTick.price;
                return 1;
            }
            if( aTick.price < extreme ){
                extreme = aTick.price;
                return 0;
            }
        }else if( type == 1 ){
            if( (aTick.price - extreme) / aTick.price <= -deltaDown ){
                type = -1;
                extreme = aTick.price;
                return -1;
            }
            if( aTick.price > extreme ){
                extreme = aTick.price;
                return 0;
            }
        }

        return 0;
    }


}