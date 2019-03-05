import market.Price;

/**
 */
public class Runner {
    public double extreme;
    public double deltaUp;
    public double deltaDown;
    public int type;
    public boolean initalized;
    public boolean absolute;

    public Runner(double threshUp, double threshDown, int type, boolean absolute){
        this.type = type; deltaUp = threshUp; deltaDown = threshDown; initalized = true; this.absolute = absolute;
    }


    public int run(Price aTick){

        if( aTick == null )
            return 0;

        if( !initalized ){
            initalized = true;
            extreme = aTick.getMid();
            return 0;
        }

        if (absolute){
            return absoluteRun(aTick);
        } else {
            return relativeRun(aTick);
        }


    }


    private int absoluteRun(Price aTick){
        if( type == -1 ){
            if( (aTick.getMid() - extreme) >= deltaUp ){
                type = 1;
                extreme = aTick.getMid();
                return 1;
            }
            if( aTick.getMid() < extreme ){
                extreme = aTick.getMid();
                return 0;
            }
        }else if( type == 1 ){
            if( (aTick.getMid() - extreme) <= -deltaDown ){
                type = -1;
                extreme = aTick.getMid();
                return -1;
            }
            if( aTick.getMid() > extreme ){
                extreme = aTick.getMid();
                return 0;
            }
        }

        return 0;
    }

    private int relativeRun(Price aTick){
        if( type == -1 ){
            if( (aTick.getMid() - extreme) / extreme >= deltaUp ){
                type = 1;
                extreme = aTick.getMid();
                return 1;
            }
            if( aTick.getMid() < extreme ){
                extreme = aTick.getMid();
                return 0;
            }
        }else if( type == 1 ){
            if( (aTick.getMid() - extreme) / aTick.getMid() <= -deltaDown ){
                type = -1;
                extreme = aTick.getMid();
                return -1;
            }
            if( aTick.getMid() > extreme ){
                extreme = aTick.getMid();
                return 0;
            }
        }

        return 0;
    }


}