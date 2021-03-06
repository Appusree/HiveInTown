package com.hiveTown.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.hiveTown.Exception.HTWebConsoleException;

public class UserAccountDAO extends SimpleJdbcDaoSupport {

	private static String checkEmailIdQuery = "select count(*) from USER_ACCOUNT "
			+ " where EMAIL_ID=?";

	public boolean getUsersByEmail(String email) throws HTWebConsoleException {

		int users = 0;
		boolean isUserAvailable = false;

		try {
			// security review comment
			if ((null != email) && (email.trim().length() > 0)) {
				// executing the query
				users = getJdbcTemplate().queryForInt(checkEmailIdQuery,
						new Object[] { email });

				if (users > 0) {
					isUserAvailable = true;

				}
			}
		} catch (DataAccessResourceFailureException e) {
			throw new HTWebConsoleException("SERVER_NOT_FOUND", e);
		} catch (DataAccessException e) {
			throw new HTWebConsoleException("DB_QUERY_FAILURE", e);
		} catch (Exception e) {
			throw new HTWebConsoleException("DB_EXCEPTION", e);
		}

		return isUserAvailable;
	}

}
