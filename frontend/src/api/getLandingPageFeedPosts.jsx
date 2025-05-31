import axios from 'axios';
import qs from 'qs';
import { DATA } from "@/app/data";

export default async function getFeedPosts(page = 0, size = 10, userId = 1, tags = []) {
    try {
        const queryParams = {
            page,
            size,
            userId,
            ...(tags.length > 0 ? { tags } : {})
        };

        const response = await axios.get(`${DATA.apiURL}/posts/landingPageFeed`, {
            params: queryParams,
            paramsSerializer: (params) =>
                qs.stringify(params, { arrayFormat: 'repeat' }),
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
