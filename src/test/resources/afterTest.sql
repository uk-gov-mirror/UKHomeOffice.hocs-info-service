DELETE FROM parent_topic_team WHERE tenant_role = 'TEST_TENANT';
DELETE FROM parent_topic WHERE UUID = '94a10f9f-a42e-44c0-8ebe-1227cb347f1d' OR UUID = '1abf7a0c-ea2d-478d-b6c8-d739fb60ef04';

DELETE FROM permission WHERE case_type = 'CT1' OR case_type = 'CT2' OR case_type = 'CT3' OR case_type = 'CT4' OR case_type = 'CT5' OR case_type = 'CT6';
DELETE FROM unit_case_type WHERE unit_uuid = '09221c48-b916-47df-9aa0-a0194f86f6dd';

DELETE FROM team WHERE unit_uuid = '09221c48-b916-47df-9aa0-a0194f86f6dd' OR unit_uuid = '65996106-91a5-44bf-bc92-a6c2f691f062';
DELETE FROM unit WHERE uuid = '09221c48-b916-47df-9aa0-a0194f86f6dd' OR uuid = '65996106-91a5-44bf-bc92-a6c2f691f062';
DELETE FROM case_type WHERE type = 'CT1' OR type = 'CT2' OR type = 'CT3' OR type = 'CT4' OR type = 'CT5' OR type = 'CT6';
DELETE FROM tenant WHERE role = 'TEST_TENANT' OR role = 'TEST2_TENANT';

