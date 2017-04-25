package calculo;

import estruturas.*;
import java.util.ArrayList;

public class Cost231 extends Problema {

    @Override
    public double avalia(double individuo[]) {
        for (Celula c : this.planta.celulas) {
            c.setPotencia(-1000);
        }

        this.planta.pas = new ArrayList<PontoAcesso>();
        for (int i = 0; i < (individuo.length / 2); i++) {
            this.planta.pas.add(new PontoAcesso(individuo[i * 2], individuo[(i * 2) + 1]));
        }

        double db = 0;
        int cmaior24 = 0, cmenor0 = 0;
        for (Celula c : this.planta.celulas) {
            for (PontoAcesso pa : this.planta.pas) {
                db = 20 - 45 - 10 * 1.4 * Math.log10(Math.sqrt(Math.pow(pa.getX() - (c.getX() + this.planta.d / 2), 2) + Math.pow(pa.getY() - (c.getY() + this.planta.d / 2), 2)));

                for (Parede p : this.planta.paredes) {
                    db -= interseccao(pa.getX(), pa.getY(), c.getX() + this.planta.d / 2, c.getY() + this.planta.d / 2, p.getReta().getX1(), p.getReta().getY1(), p.getReta().getX2(), p.getReta().getY2()) * p.getPerda();
                }

                if (db > c.getPotencia()) {
                    c.setPotencia(db);
                }
            }

            if (c.getPotencia() < -89) {
                cmenor0++;
            }
            if (c.getPotencia() > -72) {
                cmaior24++;
            }
        }
        double qualidade = (100.0 * (cmaior24) / this.planta.celulas.size()) - 1000.0 * (cmenor0 / this.planta.celulas.size());
        return qualidade;
    }

    private int interseccao(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
            double y4) {
        double d = (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3);
        if (d == 0) {
            return 0;
        }
        double ta = ((y3 - y4) * (x1 - x3) + (x4 - x3) * (y1 - y3)) / d;
        double tb = ((y1 - y2) * (x1 - x3) + (x2 - x1) * (y1 - y3)) / d;
        return (((ta >= 0) && (ta <= 1)) && ((tb >= 0) && (tb <= 1))) ? 1 : 0;
    }
}
