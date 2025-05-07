import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getFeedPosts(page = 0, size = 10) {
    try {
        const response = await axios.get(`${DATA.apiURL}/posts/landingPageFeed`, {
            params: { page, size },
            headers: { 'Content-Type': 'application/json' },
        })

        return response.data;
    } catch (error) {
        console.error("Error fetching feed posts:", error);
        throw error;
    }
}