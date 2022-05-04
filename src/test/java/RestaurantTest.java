import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //CODE REFACTOR
    @BeforeEach
    public void beforeEachTestCase(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurant_mock_open = Mockito.spy(restaurant);
        restaurant_mock_open.closingTime = LocalTime.parse("22:00:00");
        restaurant_mock_open.openingTime = LocalTime.parse("10:30:00");
        LocalTime inBetweenTime = LocalTime.parse("11:30:00");

        Mockito.when(restaurant_mock_open.getCurrentTime()).thenReturn((inBetweenTime));

        assertTrue(restaurant_mock_open.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        Restaurant restaurant_mock_close = Mockito.spy(restaurant);
        restaurant_mock_close.closingTime = LocalTime.parse("22:00:00");
        restaurant_mock_close.openingTime = LocalTime.parse("10:30:00");
        LocalTime outSideTime = LocalTime.parse("23:00:00");

        Mockito.when(restaurant_mock_close.getCurrentTime()).thenReturn((outSideTime));
        assertFalse(restaurant_mock_close.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>





    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Part3-TDD Approach
    @Test
    public void when_the_item_list_is_not_empty_the_order_value_is_positive(){
        List<String> orderItems = new ArrayList<String>();
        orderItems.add("Sweet corn soup");
        assertTrue(restaurant.calculateOrderCost(orderItems) > 0);
    }

    @Test
    public  void when_the_order_list_is_empty_the_order_cost_is_zero(){
        List<String> orderItems = new ArrayList<String>();
        assertEquals(restaurant.calculateOrderCost(orderItems) , 0);
    }

    @Test
    public void for_the_default_restaurant_the_order_value_is_388(){
        List<String> orderItems = new ArrayList<String>();
        orderItems.add("Sweet corn soup");
        orderItems.add("Vegetable lasagne");
        assertEquals(restaurant.calculateOrderCost(orderItems) , 388);
    }




}