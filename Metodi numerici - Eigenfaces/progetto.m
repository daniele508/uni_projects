%% Funzioni

% Creazione matrice immagini vettorizzate
function images = inizializzazione(detector, percorso, random)

    folders = dir(percorso);
    folders = folders([folders.isdir]);
    folders(1) = []; %rimozione "."
    folders(1) = []; %e ".."
    if random
        folders = folders(randperm(length(folders)));
    end
    
    images = [];
    
    count = 1;
    i = 1;
    while count <= 2500 && i <= length(folders)
        folder = folders(i).name;
        
        path = fullfile(percorso, folder);
        immagini = dir(path);
        immagini(1) = []; %rimozione "."
        immagini(1) = []; % e ".."
    
        for j = 1:length(immagini)
            imgpath = fullfile(path, immagini(j).name);
            img = imread(imgpath);
            img = rgb2gray(img);
            bbox = detector(img);
            if not(isempty(bbox))
                bbox1 = bbox(1,:);
                x = bbox1(1);
                y = bbox1(2);
                w = bbox1(3);
                h = bbox1(4);
                face = img(y:y+h, x:x+w);
                face_resize = imresize(face, [64,64]);
                face_resize = reshape(face_resize', 1 ,[]);
                images = [images; face_resize];
                count = count+1;
            end
        end
        i = i+1;
    end
    images = images';

end

% Calcolo media
function media = media(M)
    
    M = double(M);
    dim = size(M);
    row = dim(1);
    col = dim(2);
    media = zeros(row, 1);
    
    sum = 0;
    
    for i = 1:row
        for j = 1:col
            sum = sum + M(i, j);
        end
        media(i, 1) = sum/col;
        sum = 0;
    end

end

%Calcolo matrice covarianza
function C = covarianza(M, media)
    
    M = double(M);
    dim = size(M);
    row = dim(1);
    col = dim(2);
    C = zeros(row,row);
    
    for j = 1:col
        x = M(:, j) - media;
        C = C + x*x';
    end
    
    C = C/col;
end

%Singular Value Decomposition
function [U, S, V] = svd_c(M)

    A = M*M';
    [U, L] = eig(A);
    U = flip(U,2);

    B = M'*M;
    [V, L] = eig(B);
    V = flip(V,2);

    dim = size(L);
    S = zeros(dim(1), 1);

    for i = 1:dim(1)
        S(i) = sqrt(L(i, i));

    end
    S = flip(S);
end

%Calcolo coefficenti
function a = proiezione(componenti, gallery, eigenfaces, media)

    gallery = double(gallery);
    dim = size(gallery);
    n_immagini = dim(2);

    a=zeros(componenti, n_immagini);

    for j = 1:n_immagini    
        M = gallery(:, j)-media;
        for i = 1:componenti
            a(i, j) = M' * eigenfaces(:, i);
        end
    end
    
end

% Ricostruizione
function x = ricostruzione(a, eigenfaces, media)

    sum = 0;
    dim = size(a);
    componenti = dim(1);

    for i = 1:componenti
        sum = sum + a(i) * eigenfaces(:, i);
    end

    x = media + sum;

end

% Acquisizione foto test
function image = acquisizione(detector)
        
    img = imread("test/test.png");

    bbox = detector(img);
    if not(isempty(bbox))
        bbox1 = bbox(1, :);
        x = bbox1(1);
        y = bbox1(2);
        w = bbox1(3);
        h = bbox1(4);
        face = img(y:y+h, x:x+w);
        face_resize = imresize(face, [64,64]);
        image = reshape(face_resize', 1, []);
    end
    image = image';

end

% Calcolo varianza
function index = varianza(S, n)
    S = S/sum(S);

    somma = 0;
    index = 1;
    dim = size(S);

    for i = 1:dim(1)
        somma = somma + S(i);
        if somma >= n
            index = i;
            break
        end
    end

end

% Distanza euclidea
function d = distanze(a_g, a_t)

    dim = size(a_g);
    n = dim(2);
    d = zeros(n, 1);

    for i = 1:n
        d(i, 1) = sqrt(sum((a_g(:, i) - a_t) .^ 2));
    end

end

% Argmin
function index = argmin(d)

    dim = size(d);
    min = d(1,1);

    for i = 2:dim(1)
        if d(i, 1) <= min
            min = d(i, 1);
            index = i;
        end
    end

end

% Mapping
function id = mapping(index)
    
    if index <= 9
        id = 1;
    elseif index <= 22
        id = 2;
    elseif index <= 34
        id = 3;
    else
        id = 4;
    end

end

%% Calcolo eigenfaces

% Inizializzazione classificatore
detector = vision.CascadeObjectDetector("haarcascade_frontalface_alt.xml");

% Inizializzazione dataset immagini
images = inizializzazione(detector, 'dataset', true);

% Creazione mean face
mean = media(images);
mean_face = uint8(reshape(mean, [64,64])');
imwrite(mean_face,"immagini/media.png")

% Calcolo matrice di covarianza
C = covarianza(images, mean);

% Singular Value Decomposition
[U, S, V] = svd_c(C);

%% Salvataggio 4 eigenfaces

for i = 1:4
    eig = uint8(rescale(reshape(U(:, i), [64,64])', 0, 255));
    imwrite(eig,"immagini/eig"+string(i)+".png")
end

%% Proiezioni

% Inizializzazione galleria e immagine di test
gallery = inizializzazione(detector, 'gallery', false);
test = acquisizione(detector);

% Percentuali varianza
p = [0.2, 0.95];
ind = zeros(2, 1);
id = zeros(2, 1);
index=zeros(1,2);

for i = 1:length(p)

    % Determinazione numero componenti
    index(i) = varianza(S, p(i));
    
    % Proiezione vettori per ciascuna immagine nella galleria
    a_g = proiezione(index(i), gallery, U, mean);
    
    % Proiezione vettore immagine di test
    a_t = proiezione(index(i), test, U, mean);
    
    % Predizione identità
    ind(i) = argmin(distanze(a_g, a_t));
    id(i) = mapping(ind(i));

end

%% Risultati

id

% Ricostruzione immagini più simili
a = proiezione(4096, gallery, U, mean);

% Immagine 1
x = uint8(reshape(ricostruzione(a(:, ind(1)), U, mean), [64,64])');
imwrite(x,"immagini/res1.png")
imshow(x)
waitforbuttonpress;

% Immagine 2
x = uint8(reshape(ricostruzione(a(:, ind(2)), U, mean), [64,64])');
imwrite(x,"immagini/res2.png")
imshow(x)