import axios from 'axios';
import qs from 'qs'; // You need to install this: `npm install qs`
import { DATA } from "@/app/data";

export default async function getFeedPosts(page = 0, size = 10, userId, tags = []) {
    try {
        const response = await axios.get(`${DATA.apiURL}/posts/landingPageFeed/${userId}`, {
            params: { page, size, tags },
            paramsSerializer: (params) => {
                return qs.stringify(params, { arrayFormat: 'repeat' });
            },
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.data;
    } catch (error) {
        console.error("Error fetching feed posts:", error);
        throw error;
    }
}
