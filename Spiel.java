import java.io.*; 
import java.util.*; 

public class Spiel
{
    static int anzahlRunden, money, amountOfMoney, setMoney, win;
    private static boolean ziffer;
    private static String farbe, stringBack, som, tipp;
    private int europa[][] = { 
            { 0,  0}, { 1,  1}, { 2, -1}, { 3,  1}, { 4, -1}, { 5,  1}, { 6, -1}, { 7,  1}, { 8, -1}, { 9,  1}, 
            {10, -1}, {11, -1}, {12,  1}, {13, -1}, {14,  1}, {15, -1}, {16,  1}, {17, -1}, {18, 1},  {19, -1},
            {20, -1}, {21,  1}, {22, -1}, {23,  1}, {24, -1}, {25,  1}, {26, -1}, {27,  1}, {28, 1},  {29, -1}, 
            {30,  1}, {31, -1}, {32,  1}, {33, -1}, {34,  1}, {35, -1}, {36,  1}
        };

    public static void main(String[] argumente) {
        
        Scanner tastatur = new Scanner(System.in);
        Spiel roulette = new Spiel();

        int eingabe;

        System.out.print("Ihr Einsatzbetrag in Euro: ");
        amountOfMoney = tastatur.nextInt();
        setMoney = 0;

        do {
            do {
                System.out.println();
                System.out.println("Ihr Guthaben: " + amountOfMoney);
                System.out.println();

                int aktuell = roulette.neueAusspielung();

                System.out.println("d1 bis d3 oder D1 bis D3 für Douzen Gewinn = Einsatz * 3");
                System.out.println("rot oder Rot für Farbe Gewinn = Einsatz * 2");
                System.out.println("schwarz oder Schwarz für Farbe Gewinn = Einsatz * 2");
                System.out.println("0 bis 36 für Zahl Gewinn = Einsatz * 35");
                System.out.println("d oder D für Douzen Gewinn = Einsatz * 3");    
                System.out.println("m oder M für Manque Gewinn = Einsatz * 2");
                System.out.println("p oder P für Passe Gewinn = Einsatz * 2");
                System.out.println("o oder O für Ungerade Gewinn = Einsatz * 2");
                System.out.println("e oder E für Gerade Gewinn = Einsatz * 2");
                
                System.out.println();

                System.out.println("Runde " + roulette.getAnzahlRunden());
                System.out.println();

                System.out.print("Auf was setzen Sie? ");
                tipp = tastatur.next();

                System.out.print("Ihr Einsatz in Euro: ");

                setMoney = tastatur.nextInt();
                if(setMoney > amountOfMoney)
                    setMoney = amountOfMoney;
                else
                    amountOfMoney = amountOfMoney - setMoney;

                ziffer = isNumeric(tipp);

                System.out.println();
                System.out.print("Die Kugel fiel auf " + roulette.getAusgespielteZahl(aktuell));

                System.out.print(", " + roulette.getAusgespielteFarbe(aktuell));

                if(aktuell == 0)
                    System.out.println();
                else {
                    System.out.print(", " + roulette.manquePasse(aktuell));
                    System.out.println(", " + roulette.definitionNumber(aktuell));
                }

                System.out.println();
                win = roulette.winAmount(setMoney, tipp, aktuell);
                System.out.println(" Sie haben " + win + " Euro gewonnen.");
                amountOfMoney = amountOfMoney + win;
                setMoney = 0;

                System.out.println();
                System.out.println();
                System.out.print(" Noch einmal spielen? '1'=ja, '2'=nein ");

                eingabe = tastatur.nextInt();

            } while((amountOfMoney != 0) && eingabe != 2);
            if((amountOfMoney == 0)) {
                System.out.println();
                System.out.println("You don´t have money anymore.");
            }
            else if(eingabe == 2) {
                System.out.println();
                System.out.println("Bye bye.");
            }
            else
            {
                System.out.println();
                System.out.println("Good luck next time.");
            }
            tastatur.close();
            System.exit(0);

        } while(eingabe != 2);
    }

    public static boolean isNumeric(String value) {
        try {
            int number = Integer.parseInt(value);
            return number < 100; 
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    public Spiel()
    {
        anzahlRunden = 0;
    }

    public int neueAusspielung() {
        anzahlRunden++;
        return (int) (Math.random() * europa.length);
    }

    public String getAusgespielteZahl(int zahl) {
        return " " + europa[zahl][0];
    }

    public String definitionNumber(int zahl) {
        if(zahl % 2 != 0)
            stringBack = "ungerade";
        else
            stringBack = "gerade";
        return stringBack;
    }

    public String getAusgespielteFarbe(int zahl) {
        switch(europa[zahl][1]) {
            case -1:
            farbe = "schwarz";
            break;
            case 0:
            farbe = "grün";
            break;
            case 1:
            farbe = "rot";
            break;
            default:
            break;
        }
        return farbe;
    }

    public String manquePasse(int zahl)
    {
        if(zahl > 18)
            som = "Passe";
        else
            som = "Manque";
        return som;
    }

    public int winAmount(int money, String tipp, int zahl) {
        if(ziffer == true && (Integer.parseInt(tipp)) == zahl) {
            money = money + (money * 35);
        }
        else if((tipp.equals("rot")     || tipp.equals("Rot"))        && europa[zahl][1] == 1  ||
        		(tipp.equals("schwarz") || tipp.equals("Schwarz"))    && europa[zahl][1] == -1 ||
        		(tipp.equals("M") && zahl <= 18) || (tipp.equals("m") && zahl <= 18)           ||
        		(tipp.equals("P") && zahl >= 19) || (tipp.equals("p") && zahl >= 19)) {
            money = money + (money * 2);
        }
        else if((tipp.equals("o") || tipp.equals("O")) && stringBack.equals("ungerade") ||
        		(tipp.equals("e") || tipp.equals("E")) && stringBack.equals("gerade")) {
        	money = money + (money * 2);
    	}
        else if(((tipp.equals("D1") || (tipp.equals("d1")) && (zahl >  0 && zahl < 13)))
             || ((tipp.equals("D2") || (tipp.equals("d2")) && (zahl > 12 && zahl < 25)))
             || ((tipp.equals("D3") || (tipp.equals("d3")) && (zahl > 24 && zahl < 37)))) {
            money = money + (money * 3);
        }
        else {
            money = 0;
        }
        return money;
    }

    public int getAnzahlRunden() {
        return anzahlRunden;
    }
}
