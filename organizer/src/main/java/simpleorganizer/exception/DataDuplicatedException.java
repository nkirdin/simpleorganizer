/*
 * 
 * DataDuplicatedException 
 * is part of Simple Organizer
 *
 *
 * Copyright (С) 2016 Nikolay Kirdin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License Version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License Version 3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License Version 3 along with this program.  If not, see 
 * <http://www.gnu.org/licenses/>.
 *
 */

package simpleorganizer.exception;

import javax.ejb.ApplicationException;

/**
 * 
 * @author user
 * The DataDuplicatedException is used for indicating duplicated data in fields with unique = true flag. 
 */
@ApplicationException(rollback = true)
public class DataDuplicatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataDuplicatedException() {}

    public DataDuplicatedException(String message){
        super(message);
    }

    public DataDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataDuplicatedException(Throwable cause) {
        super(cause);
    }

}
