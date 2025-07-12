import com.google.gson.Gson;
import org.it.uniba.fox.Entity.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inizializer {

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();

        items.add(new Item(
                "Pistola",
                "Una pistola semiautomatica in dotazione standard dell’esercito degli Stati Uniti, modello Beretta 92FS. E’ dotata di silenziatore e munizioni stordenti.",
                true,
                true,
                Arrays.asList("Beretta", "Arma", "PistolaSilenziata")
        ));

        items.add(new Item(
                "Sigarette",
                "Avvertenza del ministero della salute: fumare può essere pericoloso per la salute. Ha un uso limitato nel rivelare la posizione dei sensori a infrarossi.",
                false,
                true,
                Arrays.asList("Pacchetto", "Fumo", "SigaretteAmericane")
        ));



        Gson gson = new Gson();
        String json = gson.toJson(items);

        System.out.println(json);
    }
}