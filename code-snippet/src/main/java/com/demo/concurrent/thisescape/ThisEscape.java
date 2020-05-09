package com.demo.concurrent.thisescape;

public class ThisEscape {

    public final int id;
    public final String name;

    /**
     * this逃逸
     */
//    public ThisEscape(EventSource<EventListener> source){
//        id = 1;
//        source.registerListener(new EventListener(){
//            @Override
//            public void onEvent(String [] str){
//                str[1] = "bbbbbb";
//                //在这里
//                System.out.println("id : "+ThisEscape.this.id);//1
//                System.out.println("name : "+ThisEscape.this.name);//null
//            }
//        });
//        //构造函数还没有执行完,但是在外面已经能访问到这个类的属性了
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        name = "thisEscape";
//    }

    /**
     * 防止this逃逸
     */
    private final EventListener listener;
    private ThisEscape () {
        id = 1;
        listener = new EventListener(){
            @Override
            public void onEvent(String [] str){
                str[1] = "bbbbbb";
                //在这里
                System.out.println("id : "+ThisEscape.this.id);//1
                System.out.println("name : "+ThisEscape.this.name);//null
            }
        };
        name = "thisEscape";
    }
    public static ThisEscape getInstance(EventSource<EventListener> source) {
        ThisEscape thisEscape = new ThisEscape();
        source.registerListener(thisEscape.listener);
        return thisEscape;
    }
}
