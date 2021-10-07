public class S19317_p2 {

    public static void main(String[] args) {

    MyPoint myPoint = new MyPoint(3.14f,6.28f);
    myPoint.getMatrix();
    myPoint.show();
    System.out.println("Triangle:");
    Triangle triangle = new Triangle(new float[]{-1,3,3},new float[]{0,0,3});
    triangle.show();
    System.out.println();
    triangle.rotate(30);
    triangle.show();
    System.out.println();
    triangle.translate(2,2);
    triangle.show();
    System.out.println();
    triangle.scale(2,2);
    triangle.show();
    System.out.println();
    System.out.println("Circle:");
    Circle circle1 = new Circle();
    Circle circle2 = new Circle(7);
    circle1.show();
    System.out.println();
    circle2.show();
    System.out.println();
    circle2.rotate(45);
    circle2.show();
    System.out.println();
    circle2.translate(3,2);
    circle2.show();
    System.out.println();
    circle2.scale(2,4);
    circle2.show();
    }
    public static class MyPoint {
        private float x;
        private float y;

        public MyPoint(float x, float y) {
            this.x=x;
            this.y=y;
        }
        float [][] getMatrix(){
            return new float[][]{{x},
                    {y},
                    {1}};
        }
        void show(){
            System.out.println("MyPoint x: "+x+" y:"+y);
        }

    }

    public static class AffineTransform {
        public static float[][] getTranslateMat(float vx, float vy){
            float tmp[][] = {{1,0,vx},
                    {0,1,vy},
                    {0,0,1}};
            return tmp;
        }
        public static float[][] getRotateMax(float phi){
            float tmp[][] = {{(float)Math.cos(phi),(float)Math.sin(phi),0},
                    {-(float)Math.sin(phi),(float)Math.cos(phi),0},
                    {0,0,1}};
            return tmp;
        }
        public static float[][] getScaleMat(float w, float h){
            float tmp[][] = {{w,0,0},
                    {0,h,0},
                    {0,0,1}};
            return tmp;
        }
        public static MyPoint mul(float[][] tab, MyPoint mp){
            float mnozenieTab=1;
            for(int i=0;i<tab[0].length;i++){
                mnozenieTab*=tab[0][i];
                for(int j=0; j<3;j++){
                    mp.getMatrix()[j][0]+=mnozenieTab;
                }
            }
            return mp;
        }
    }
    public static class Triangle {
        private MyPoint a;
        private MyPoint b;
        private MyPoint c;
        private MyPoint[] triangle = {a, b, c};

        public Triangle(float[] tabX, float[] tabY) {
            for (int i = 0; i < triangle.length; i++)
                triangle[i] = new MyPoint(tabX[i], tabY[i]);

        }

        void show() {
            for (MyPoint myPoint : triangle)
                myPoint.show();
        }

        void translate(float vx, float vy){
            for (int i = 0; i < triangle.length; i++) {
                triangle[i]=AffineTransform.mul(AffineTransform.getTranslateMat(vx,vy), triangle[i]);
            }

        }
        void rotate(float phi){
            for (int i = 0; i < triangle.length; i++) {
                triangle[i]=AffineTransform.mul(AffineTransform.getRotateMax(phi), triangle[i]);
            }
        }
        void scale(float w, float h){
            for (int i = 0; i < triangle.length; i++) {
                triangle[i]=AffineTransform.mul(AffineTransform.getScaleMat(w,h), triangle[i]);
            }
        }
    }

    public static class Circle {
        private MyPoint center = new MyPoint(0,0);
        private int promien;
        private MyPoint[] zbior;
        public Circle(){
            // x = x0 + R cos a,
            // y = y0 + R sin a,
            this.promien = 5;
            int odstepy=10;
            split(odstepy, promien);
        }

        public Circle(int promien){
            this.promien = promien;
            int odstepy=12;
            split(odstepy, promien);
        }
        private void split(int odstepy, int promien) {
            this.zbior = new MyPoint[odstepy];
            zbior[0]=new MyPoint(0, promien);
            double kat = 360/odstepy;
            for(int i=1;i<zbior.length;i++){
                zbior[i]=new MyPoint(zbior[i-1].getMatrix()[0][0]+(float)(promien *Math.cos(kat)),zbior[i-1].getMatrix()[0][1]+(float)(promien *Math.sin(kat)));
            }
        }
        void show(){
            for (MyPoint myPoint : zbior)
                myPoint.show();
        }
        void translate(float vx, float vy){
            for (int i = 0; i < zbior.length; i++) {
                zbior[i]=AffineTransform.mul(AffineTransform.getTranslateMat(vx,vy), zbior[i]);
            }

        }
        void rotate(float phi){
            for (int i = 0; i < zbior.length; i++) {
                zbior[i]=AffineTransform.mul(AffineTransform.getRotateMax(phi), zbior[i]);
            }
        }
        void scale(float w, float h){
            for (int i = 0; i < zbior.length; i++) {
                zbior[i]=AffineTransform.mul(AffineTransform.getScaleMat(w,h), zbior[i]);
            }
        }
    }
}
