package corr.ai.module.metadata.dal.typehandler;

import cn.hutool.json.JSONObject;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dongchengye
 */
public class JsonListHandler implements TypeHandler<List<JSONObject>> {
    @Override
    public void setParameter(PreparedStatement ps, int i, List<JSONObject> parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public List<JSONObject> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<JSONObject> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<JSONObject> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
