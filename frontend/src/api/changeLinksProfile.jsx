import axios from 'axios';
import { DATA } from "@/app/data"

export default async function changeLinksProfile({ github, twitter, website }) {
    try {

        const bodyData = {
            github_link: github,
            twitter_link: twitter,
            website_link: website
        }

        const response = await axios.post(`${DATA.apiURL}/user/change-links`, {
            bodyData
        });

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}