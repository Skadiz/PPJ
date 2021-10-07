public class S19317_p01 {

    public static void main(String[] args) {
        java.text.SimpleDateFormat formatter =
                new java.text.SimpleDateFormat("yyyy -MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        char[] tab = formatter.format(date).toCharArray();
        System.out.println(tab);
        printMonth(toLong(tab));
    }

    public static long toLong(char[] tab) {
        long date;
        int[] dateNumbers = new int[5];
        int j = 1;
        int sum = 0;
        dateNumbers[0] = (tab[0] - 48) * 1000 + (tab[1] - 48) * 100 + (tab[2] - 48) * 10 + (tab[3] - 48);
        for (int i = 6; i < 19; i++) {
            if (tab[i] > 47 && tab[i] < 57) {
                sum += (tab[i] - 48) * 10 + (tab[i + 1] - 48);
                i++;
            } else {
                dateNumbers[j] = sum;
                sum = 0;
                j++;
            }
        }
        date = dateNumbers[0] << 4;
        date = date | dateNumbers[1];
        date = date << 8;
        date = date | dateNumbers[2];
        date = date << 8;
        date = date | dateNumbers[3];
        date = date << 16;
        date = date | dateNumbers[4];
        return date;
    }

    public static void printMonth(long date) {
//        0 => Poniedziałek
//        1 => Wtorek
//        2 => Środa
//        3 => Czwartek
//        4 => Piątek
//        5 => Sobota
//        6 => Niedziela
        long[] week = {0, 1, 2, 3, 4, 5, 6};
        boolean flag = false;
        long year = date >> 36;
        long month = date >> 32 & 0b0000_1111;
        long day = date >> 24 & 0b0000_0000_1111_1111;
        long weekday = getStartOfMonth(year, month);
        int i = 1;
        int j = 1;
        int count = 1;
        while (j <= getNumberOfDays(year, month)) {
            if (!flag) {
                if (week[i] != weekday) {
                    System.out.print(" " + "\t");
                    count++;
                    if (count == 7) {
                        System.out.println("");
                        count = 0;
                    }
                } else if(week[i]==weekday){
                    System.out.print(" " + "\t");
                    if(j==day)
                        System.out.print(">"+j++ + "<"+"\t");
                    else if(j<10) System.out.print(" "+j+++"\t"); else System.out.print(j+++"\t");
                    count++;
                    if (count == 7) {
                        System.out.println("");
                        count = 0;
                    }
                    flag = true;
                }
                i++;
            } else {
                if(j==day)
                    System.out.print(">"+j++ + "<"+"\t");
                else if(j<10) System.out.print(" "+j+++"\t"); else System.out.print(j+++"\t");
                count++;
                if(count==7){
                    System.out.println("");
                    count=0;
                }
            }
        }
    }

    public static boolean isLeap(long year) {
        if ((year % 400) == 0) return true;
        if ((year % 100) == 0) return false;
        if ((year % 4) == 0) return true;
        return false;
    }

    public static long getStartOfMonth(long year, long month) {
        long shifts[] = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
        long day = 1;
        long shift = shifts[(int) month - 1];
        if (isLeap(year) && (month > 2)) {
            shift += 1;
        }
        year = (year - 1) % 400;
        long century = year / 100;
        long index = ((4 * century) + (year % 100)) % 28;
        long weekday = (index + (index / 4)) + shift + (day - 1);
        return (weekday % 7);
    }

    public static long getNumberOfDays(long year, long month) {
        if (month == 2)
            if (isLeap(year))
                return 29;
            else return 28;
        if (month <= 7) {
            if (month % 2 == 0)
                return 30;
            else return 31;
        } else {
            if (month % 2 == 0)
                return 31;
            else return 30;
        }
    }
}

