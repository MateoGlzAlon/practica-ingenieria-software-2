import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";

function ProjectImageCarousel({ projectImages }) {
    const responsive = {
        superLargeDesktop: { breakpoint: { max: 4000, min: 3000 }, items: 1 },
        desktop: { breakpoint: { max: 3000, min: 1024 }, items: 1 },
        tablet: { breakpoint: { max: 1024, min: 640 }, items: 1 },
        mobile: { breakpoint: { max: 640, min: 0 }, items: 1 },
    };

    return (
        <Carousel
            responsive={responsive}
            showDots
            arrows
            containerClass="carousel-container"
            dotListClass="custom-dot-list-style"
        >
            {projectImages.map((image, idx) => (
                <div
                    key={idx}
                    className="flex items-center justify-center h-[500px] w-full overflow-hidden mx-auto"
                >
                    <img
                        src={image}
                        alt={`Project image ${idx + 1}`}
                        className="rounded-xl shadow-md border object-cover max-h-[480px]"
                    />
                </div>
            ))}
        </Carousel>
    );
}

export default ProjectImageCarousel;
