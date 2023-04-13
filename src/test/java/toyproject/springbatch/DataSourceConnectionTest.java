package toyproject.springbatch;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import toyproject.repository.batch.BatchRepository;
import toyproject.repository.blog.BlogRespository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceConnectionTest {

    @Autowired
    BatchRepository commonRepository;

    @Autowired
    BlogRespository blogRespository;

    @Test
    public void batchDataSourceConnection() throws Exception {
        assertTrue(commonRepository != null, "Batch DB Connection Success!");
    }

    @Test
    public void blogDataSourceConnection() throws Exception {
        assertTrue(blogRespository != null, "Blog Db Connection Success!");
    }
}
