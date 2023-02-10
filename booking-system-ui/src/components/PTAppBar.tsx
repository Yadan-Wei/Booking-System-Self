import { AppBar, Button, Toolbar, Typography } from '@mui/material';
import React, { useCallback, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import BookingSystemContext from '../context/BookingSystemContext';
import iconImage from '../static/logo.png';

const styles = {
    nameText: {
        flexGrow: 1
    },
    iconImage: {
        width: '60px',
        height: '60px',
        marginRight: '20px',
    }
}

export default function PTAppBar(): React.ReactElement {
    const history = useNavigate();
    const { username, role, setUserName, setRole } = useContext(BookingSystemContext);
    const onLoginButtonClick = useCallback(() => {
        switch (role) {
            case 'Instructor':
                history(`/instructor/${username}`);
                break;
            case 'Student':
                history(`/student/${username}`);
                break;
            default:
                history('/login');
        }
    }, [history, role, username]);
    const onLogOutButtonClick = useCallback(() => {
        localStorage.clear();
        setUserName(null);
        setRole(null);
        history('/login');
    }, [history, setRole, setUserName])
    const buttonText = username ?? 'Log in';

    return (
        <AppBar>
            <Toolbar>
                <img src={iconImage} alt='Pivot Tech Logo' style={styles.iconImage} />
                <Typography variant="h6" component="div" sx={styles.nameText}>
                    Booking System
                </Typography>
                <Button color="inherit" onClick={onLoginButtonClick}>{buttonText}</Button>
                {Boolean(username) && (
                    <Button color="inherit" onClick={onLogOutButtonClick}>Log Out</Button>
                )}
            </Toolbar>
        </AppBar>
    )

}