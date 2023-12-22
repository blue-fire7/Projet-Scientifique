INSERT INTO public.sensor
(id, latitude, longitude)
values
	(nextval('sensor_id_seq'::regclass), 45.786191, 4.86105),
	(nextval('sensor_id_seq'::regclass), 45.785836, 4.903327),
	(nextval('sensor_id_seq'::regclass), 45.772856, 4.859939),
	(nextval('sensor_id_seq'::regclass), 45.772223, 4.901491),
	(nextval('sensor_id_seq'::regclass), 45.779523499999996, 4.8604945),
	(nextval('sensor_id_seq'::regclass), 45.7790295, 4.9024090000000005),
	(nextval('sensor_id_seq'::regclass), 45.786151555555556, 4.865747444444444),
	(nextval('sensor_id_seq'::regclass), 45.786112111111116, 4.870444888888889),
	(nextval('sensor_id_seq'::regclass), 45.78607266666667, 4.875142333333333),
	(nextval('sensor_id_seq'::regclass), 45.78603322222222, 4.879839777777778),
	(nextval('sensor_id_seq'::regclass), 45.78599377777778, 4.884537222222222),
	(nextval('sensor_id_seq'::regclass), 45.785954333333336, 4.889234666666667),
	(nextval('sensor_id_seq'::regclass), 45.78591488888889, 4.893932111111111),
	(nextval('sensor_id_seq'::regclass), 45.78587544444445, 4.898629555555556),
	(nextval('sensor_id_seq'::regclass), 45.779468611111106, 4.865151666666667),
	(nextval('sensor_id_seq'::regclass), 45.779413722222216, 4.869808833333333),
	(nextval('sensor_id_seq'::regclass), 45.77935883333333, 4.874466),
	(nextval('sensor_id_seq'::regclass), 45.77930394444444, 4.879123166666667),
	(nextval('sensor_id_seq'::regclass), 45.77924905555555, 4.883780333333333),
	(nextval('sensor_id_seq'::regclass), 45.77919416666666, 4.8884375),
	(nextval('sensor_id_seq'::regclass), 45.77913927777778, 4.893094666666667),
	(nextval('sensor_id_seq'::regclass), 45.77908438888889, 4.897751833333333),
	(nextval('sensor_id_seq'::regclass), 45.772785666666664, 4.864555888888889),
	(nextval('sensor_id_seq'::regclass), 45.77271533333333, 4.869172777777778),
	(nextval('sensor_id_seq'::regclass), 45.772645, 4.873789666666666),
	(nextval('sensor_id_seq'::regclass), 45.772574666666664, 4.878406555555555),
	(nextval('sensor_id_seq'::regclass), 45.77250433333333, 4.8830234444444445),
	(nextval('sensor_id_seq'::regclass), 45.772434, 4.887640333333334),
	(nextval('sensor_id_seq'::regclass), 45.772363666666664, 4.892257222222222),
	(nextval('sensor_id_seq'::regclass), 45.77229333333333, 4.896874111111111),
	(nextval('sensor_id_seq'::regclass), 45.770707, 4.8766),
	(nextval('sensor_id_seq'::regclass), 45.767617, 4.9175),
	(nextval('sensor_id_seq'::regclass), 45.756226, 4.876393),
	(nextval('sensor_id_seq'::regclass), 45.749442, 4.916804),
	(nextval('sensor_id_seq'::regclass), 45.7634665, 4.8764965),
	(nextval('sensor_id_seq'::regclass), 45.7585295, 4.917152),
	(nextval('sensor_id_seq'::regclass), 45.77036366666667, 4.881144444444445),
	(nextval('sensor_id_seq'::regclass), 45.770020333333335, 4.885688888888889),
	(nextval('sensor_id_seq'::regclass), 45.769677, 4.890233333333334),
	(nextval('sensor_id_seq'::regclass), 45.76933366666667, 4.894777777777778),
	(nextval('sensor_id_seq'::regclass), 45.768990333333335, 4.899322222222223),
	(nextval('sensor_id_seq'::regclass), 45.768647, 4.903866666666667),
	(nextval('sensor_id_seq'::regclass), 45.76830366666667, 4.9084111111111115),
	(nextval('sensor_id_seq'::regclass), 45.767960333333335, 4.9129555555555555),
	(nextval('sensor_id_seq'::regclass), 45.762917944444446, 4.881013777777778),
	(nextval('sensor_id_seq'::regclass), 45.76236938888889, 4.885531055555555),
	(nextval('sensor_id_seq'::regclass), 45.76182083333333, 4.8900483333333336),
	(nextval('sensor_id_seq'::regclass), 45.76127227777778, 4.894565611111111),
	(nextval('sensor_id_seq'::regclass), 45.760723722222224, 4.899082888888889),
	(nextval('sensor_id_seq'::regclass), 45.76017516666667, 4.903600166666666),
	(nextval('sensor_id_seq'::regclass), 45.75962661111111, 4.908117444444445),
	(nextval('sensor_id_seq'::regclass), 45.759078055555555, 4.912634722222222),
	(nextval('sensor_id_seq'::regclass), 45.755472222222224, 4.880883111111111),
	(nextval('sensor_id_seq'::regclass), 45.75471844444444, 4.885373222222222),
	(nextval('sensor_id_seq'::regclass), 45.75396466666667, 4.8898633333333335),
	(nextval('sensor_id_seq'::regclass), 45.75321088888889, 4.8943534444444445),
	(nextval('sensor_id_seq'::regclass), 45.75245711111111, 4.898843555555556),
	(nextval('sensor_id_seq'::regclass), 45.75170333333333, 4.903333666666667),
	(nextval('sensor_id_seq'::regclass), 45.75094955555556, 4.907823777777778),
	(nextval('sensor_id_seq'::regclass), 45.750195777777776, 4.912313888888889);

insert into public.fire_station 
	(id, latitude, longitude)
values
	(nextval('fire_station_id_seq'::regclass), 45.765070, 4.905230),
	(nextval('fire_station_id_seq'::regclass), 45.778839, 4.878460)



insert into public.team
	(id, latitude, longitude)
values
	(nextval('team_id_seq'::regclass), 'Alpha'),
	(nextval('team_id_seq'::regclass), 'Bravo'),
	(nextval('team_id_seq'::regclass), 'Coca'),
	(nextval('team_id_seq'::regclass), 'Delta'),
	(nextval('team_id_seq'::regclass), 'Echo'),
	(nextval('team_id_seq'::regclass), 'Foxtrot'),


insert into fire_truck_type 
(id, type_label, power_factor, speed)
values 
	(1, 'grande échelle', 5, 80),
	(2, 'pompe-tonne', 8, 50);


INSERT INTO public.fire_truck
(id, fire_station_id, fire_truck_type_id, longitude, latitude)
VALUES(nextval('fire_truck_id_seq'::regclass), 1, 1, null, null),
(nextval('fire_truck_id_seq'::regclass), 1, 1, null, null),
(nextval('fire_truck_id_seq'::regclass), 1, 2, null, null),
(nextval('fire_truck_id_seq'::regclass), 2, 1, null, null),
(nextval('fire_truck_id_seq'::regclass), 2, 1, null, null),
(nextval('fire_truck_id_seq'::regclass), 2, 2, null, null);



