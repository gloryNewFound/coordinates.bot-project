package dvn.coordinates_bot.coordinates.bot;

import dvn.coordinates_bot.coordinates.bot.controller.AnswerConfigurator;
import dvn.coordinates_bot.coordinates.bot.regions.Region;

public class Test {

    public static void main(String[] args) {
        String address = "Санкт-Петербург, Учебный пер. 6 кор 1";
        for (Region region: Region.values()) {
            System.out.println(region.getName());
        }
    }
}
