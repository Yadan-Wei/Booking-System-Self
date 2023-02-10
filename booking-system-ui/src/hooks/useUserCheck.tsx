import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import BookingSystemRequest from "../utils/BookingSystemRequest";
import { RouteParams } from "../views/InstructorView";

export default function useUserCheck (
    notFound: (parsedRes: any) => boolean
): boolean {
    const [isLoading, setIsLoading] = useState(false);

    const { username: routeUsername } = useParams<RouteParams>();
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoading(true);
        new BookingSystemRequest(`users/${routeUsername}`, 'GET')
            .onSuccess((res: string) => {
                if (!Boolean(res)) {
                    navigate('/');
                    return;
                }
                const parsedRes = JSON.parse(res);
                if (notFound(parsedRes)) {
                    navigate('/');
                }
                setIsLoading(false);
            })
            .onFailure(() => navigate('/'))
            .send()
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return isLoading;
}