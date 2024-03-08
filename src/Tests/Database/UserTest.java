package Tests.Database;

import Main.Database.User;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;


class UserTest {

    /**
     * Simple check if only one user instance is created with getInstance method.
     */
    @Test
    public void testSingletonInstance() {
        User instance1 = User.getInstance();
        User instance2 = User.getInstance();
        assertSame(instance1, instance2, "Instances should be the same");
    }

    /**
     * Tests the thread safety of the User Singleton instance creation.
     *
     * This test simulates two threads attempting to get the Singleton instance simultaneously.
     * It uses a CountDownLatch to synchronize the threads and ensure they both complete before
     * asserting that they obtained the same instance.
     *
     * @throws InterruptedException If an interruption occurs while waiting for threads to finish.
     */
    @Test
    public void testThreadSafety() throws InterruptedException{
        // Create a latch with a count of 2 to simulate two threads
        CountDownLatch latch = new CountDownLatch(2);
        User[] users = new User[2];
        // Create two threads that will attempt to get the Singleton instance
        Thread thread1 = new Thread(() -> {
            users[0] = User.getInstance();
            latch.countDown();
        });

        Thread thread2 = new Thread(() -> {
            users[1] = User.getInstance();
            latch.countDown();
        });

        // Start both threads
        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        latch.await();

        // Get the Singleton instance outside of threads
        User user = User.getInstance();

        // Assert that both threads obtained the same instance
        assertSame(user, users[0]);
        assertSame(user, users[1]);
    }

    /**
     * Simply tests if username set and get method works with User instance
     */
    @Test
    void testSetAndGetUsername() {
        User.getInstance().setUsername("User");
        assertEquals("User", User.getInstance().getUsername(), "Username should be set and retrieved as same");
    }


}
