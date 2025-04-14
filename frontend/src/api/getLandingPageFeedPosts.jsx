import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getFeedPosts() {
    try {
        const response = await axios.get(`${DATA.apiURL}/posts/landingPageFeed`, {
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
