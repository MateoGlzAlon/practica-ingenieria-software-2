.PHONY: docker.test_reports

docker.test_reports:
	docker build -f Dockerfile -t gradle-test ..
	@container_id=$$(docker create gradle-test); \
	docker cp $$container_id:/output ../buildDocker; \
	docker rm $$container_id
